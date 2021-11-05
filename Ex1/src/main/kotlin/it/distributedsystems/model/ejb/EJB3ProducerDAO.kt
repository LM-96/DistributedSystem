package it.distributedsystems.model.ejb

import it.distributedsystems.model.dao.CustomerDAO
import it.distributedsystems.model.dao.Producer
import it.distributedsystems.model.dao.ProducerDAO
import javax.ejb.Local
import javax.ejb.Stateless
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//import it.distributedsystems.model.logging.OperationLogger;
@Stateless
@Local(CustomerDAO::class) //@Remote(CustomerDAO.class) //-> TODO: serve nella versione clustering???
class EJB3ProducerDAO : ProducerDAO {
    @PersistenceContext(unitName = "distributed-systems-demo")
    var em: EntityManager? = null

    //    @Interceptors(OperationLogger.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun insertProducer(producer: Producer): Int {
        em!!.persist(producer)
        return producer.id
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeProducerByName(name: String): Int {
        val producer: Producer
        return if (name != null && name != "") {
            producer = em!!.createQuery("FROM Producer p WHERE p.name = :producerName")
                .setParameter("producerName", name)
                .singleResult as Producer
            em!!.remove(producer)
            producer.id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeProducerById(id: Int): Int {
        val producer = em!!.find(
            Producer::class.java, id
        )
        return if (producer != null) {
            em!!.remove(producer)
            id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findProducerByName(name: String): Producer? {
        return if (name != null && name != "") {
            em!!.createQuery("FROM Producer p where p.name = :producerName")
                .setParameter("producerName", name).singleResult as Producer
        } else null
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findProducerById(id: Int): Producer? {
        return em!!.find(Producer::class.java, id)
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllProducers(): List<Producer> {
        return em!!.createQuery("FROM Producer").resultList as List<Producer>
    }
}