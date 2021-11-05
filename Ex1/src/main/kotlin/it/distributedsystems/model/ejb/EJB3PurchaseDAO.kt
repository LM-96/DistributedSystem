package it.distributedsystems.model.ejb

import it.distributedsystems.model.dao.PurchaseDAO
import it.distributedsystems.model.dao.Purchase
import it.distributedsystems.model.dao.Customer
import it.distributedsystems.model.dao.Product
import java.util.HashSet
import javax.ejb.Local
import javax.ejb.Stateless
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//import it.distributedsystems.model.logging.OperationLogger;
@Stateless
@Local(PurchaseDAO::class) //@Remote(PurchaseDAO.class)  //-> TODO: serve nella versione clustering???
class EJB3PurchaseDAO : PurchaseDAO {
    @PersistenceContext(unitName = "distributed-systems-demo")
    var em: EntityManager? = null

    //    @Interceptors(OperationLogger.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun insertPurchase(purchase: Purchase): Int {

        //riattacco il customer al contesto di persistenza
        if (purchase.customer != null && purchase.customer.id > 0) purchase.customer = em!!.merge(purchase.customer)

        //riattacco i product al contesto di persistenza
        val products: MutableSet<Product> = HashSet()
        if (purchase.products != null) {
            for (product in purchase.products) {
                if (product != null && product.id > 0) products.add(em!!.merge(product))
            }
            purchase.products = products
        }
        em!!.persist(purchase)
        return purchase.id
    }

    /*
    @Override
    public int removePurchaseByNumber(int purchaseNumber) {
        Purchase purchase = (Purchase) em.createQuery("DELETE FROM Purchase p WHERE p.purchaseNumber LIKE :num").
                setParameter("num", purchaseNumber).getSingleResult();
        if (purchase!=null){
            int id = purchase.getId();
            //Cancello le associazioni tra l'autore da rimuovere e i libri da lui scritti
            //dalla tabella di associazione Book_Author
            em.createNativeQuery("DELETE FROM Customer WHERE purchase_id ="+id+" ;").executeUpdate();

            em.remove(purchase);

            return id;
        }
        else
            return 0;
    }
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removePurchaseById(id: Int): Int {
        val purchase = em!!.find(Purchase::class.java, id)
        return if (purchase != null) {
            //Cancello le associazioni tra l'ordine da rimuovere e i prodotti inseriti
            //dalla tabella di associazione Purchase_Product
            em!!.createNativeQuery("DELETE FROM Purchase_Product WHERE purchase_id=" + purchase.id + " ;")
                .executeUpdate()
            em!!.remove(purchase)
            id
        } else 0
    }

    override fun findPurchaseByNumber(purchaseNumber: Int): Purchase? {
        return em!!.createQuery("select p from Purchase p where p.purchaseNumber = :num")
            .setParameter("num", purchaseNumber).singleResult as Purchase
    }

    override fun findPurchaseById(id: Int): Purchase? {
        return em!!.find(Purchase::class.java, id)
        /*
        return (Purchase) em.createQuery("FROM Purchase p WHERE p.id = :purchaseId").
			setParameter("purchaseId", id).getSingleResult();
         */
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllPurchase() : List<Purchase> {
        return em!!.createQuery("FROM Purchase p ").resultList as List<Purchase>
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findAllPurchasesByCustomer(customer: Customer): List<Purchase> {
        //Non è stato necessario usare una fetch join (nonostante Purchase.customer fosse mappato LAZY)
        //perché gli id delle entità LAZY collegate vengono comunque mantenuti e sono accessibili
        return em!!.createQuery("FROM Purchase p WHERE :customerId = p.customer.id")
            .setParameter("customerId", customer.id).resultList as List<Purchase>
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findAllPurchasesByProduct(product: Product?): List<Purchase> {
        return if (product != null) {
            em!!.merge(product) // riattacco il product al contesto di persistenza con una merge
            em!!.createQuery("SELECT DISTINCT (p) FROM Purchase p JOIN FETCH p.products JOIN FETCH p.customer WHERE :product MEMBER OF p.products")
                .setParameter("product", product).resultList as List<Purchase>
        } else em!!.createQuery("SELECT DISTINCT (p) FROM Purchase p JOIN FETCH p.products JOIN FETCH p.customer").resultList as List<Purchase>
    }
}