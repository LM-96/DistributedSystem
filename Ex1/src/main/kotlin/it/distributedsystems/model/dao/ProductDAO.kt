package it.distributedsystems.model.dao

interface ProductDAO {

    fun insertProduct(product : Product)
    fun removeProductByNumber(productNumber : Int)
    fun removeProductById(id : Int)
    fun findProductByNumber(productNumber: Int) : Product?
    fun findProductById(id: Int) : Product?
    fun getAllProducts() : List<Product>
    fun getAllProductsByProducer(producer: Producer) : List<Product>
    fun getAllProductsByPurchase(purchase: Purchase) : List<Product>

}