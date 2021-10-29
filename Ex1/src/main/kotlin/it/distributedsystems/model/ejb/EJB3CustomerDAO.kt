package it.distributedsystems.model.ejb

import it.distributedsystems.model.dao.CustomerDAO
import it.distributedsystems.model.dao.Customer
import javax.ejb.Local
import javax.ejb.Stateless
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//import it.distributedsystems.model.logging.OperationLogger;
@Stateless
@Local(CustomerDAO::class) //@Remote(CustomerDAO.class) //-> TODO: serve nella versione clustering???
class EJB3CustomerDAO : CustomerDAO {
    @PersistenceContext(unitName = "distributed-systems-demo")
    var em: EntityManager? = null

    //    @Interceptors(OperationLogger.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun insertCustomer(customer: Customer): Int {
        em!!.persist(customer)
        return customer.id
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeCustomerByName(name: String): Int {
        val customer: Customer
        return if (name != null && name != "") {
            customer = em!!.createQuery("FROM Customer c WHERE c.name = :customerName")
                .setParameter("customerName", name).singleResult as Customer
            em!!.remove(customer)
            customer.id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeCustomerById(id: Int): Int {
        val customer = em!!.find(Customer::class.java, id)
        return if (customer != null) {
            em!!.remove(customer)
            id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findCustomerByName(name: String): Customer? {
        return if (name != null && name != "") {
            em!!.createQuery("FROM Customer c where c.name = :customerName")
                .setParameter("customerName", name).singleResult as Customer
        } else null
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findCustomerById(id: Int): Customer? {
        return em!!.find(Customer::class.java, id)
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllCustomer(): List<Customer> {
        return em!!.createQuery("FROM Customer").resultList as List<Customer>
    }
}