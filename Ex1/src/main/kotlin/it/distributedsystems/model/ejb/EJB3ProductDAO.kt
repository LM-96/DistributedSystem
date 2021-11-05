package it.distributedsystems.model.ejb

import it.distributedsystems.model.dao.Producer
import it.distributedsystems.model.dao.ProductDAO
import it.distributedsystems.model.dao.Product
import it.distributedsystems.model.dao.Purchase
import javax.ejb.Local
import javax.ejb.Stateless
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//import it.distributedsystems.model.logging.OperationLogger;
@Stateless
@Local(ProductDAO::class) //@Remote(ProductDAO.class)  //-> TODO: serve nella versione clustering???
class EJB3ProductDAO : ProductDAO {
    @PersistenceContext(unitName = "distributed-systems-demo")
    var em: EntityManager? = null

    //    @Interceptors(OperationLogger.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun insertProduct(product: Product): Int {
        em!!.persist(product)
        return product.id
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeProductByNumber(productNumber: Int): Int {
        val product = em!!.createQuery("from Product p where p.name = :num")
            .setParameter("num", productNumber).singleResult as Product
        return if (product != null) {
            val id = product.id
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em!!.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id=$id ;").executeUpdate()
            em!!.remove(product)
            id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    override fun removeProductById(id: Int): Int {
        val product = em!!.find(Product::class.java, id)
        return if (product != null) {
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em!!.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id=" + product.id + " ;").executeUpdate()
            em!!.remove(product)
            id
        } else 0
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findProductByNumber(productNumber: Int): Product? {
        return em!!.createQuery("from Product p where p.productNumber = :num")
            .setParameter("num", productNumber).singleResult as Product
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun findProductById(id: Int): Product? {
        return em!!.find(Product::class.java, id)
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllProducts(): List<Product> {
        return em!!.createQuery("from Product").resultList as List<Product>
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllProductsByProducer(producer: Producer): List<Product> {
        //Non è stato necessario usare una fetch join (nonostante Product.producer fosse mappato LAZY)
        //perché gli id delle entità LAZY collegate vengono comunque mantenuti e sono accessibili
        return em!!.createQuery("FROM Product p WHERE :producerId = p.producer.id")
            .setParameter("producerId", producer.id).resultList as List<Product>
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    override fun getAllProductsByPurchase(purchase: Purchase): List<Product> {
        // riattacco il product al contesto di persistenza con una merge
        return em!!.createQuery("FROM Product p WHERE :purchaseId = p.purchase.id")
            .setParameter("purchaseId", purchase.id).resultList as List<Product>
    }
}