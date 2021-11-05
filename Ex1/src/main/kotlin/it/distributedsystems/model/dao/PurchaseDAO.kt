package it.distributedsystems.model.dao

interface PurchaseDAO {

    fun insertPurchase(purchase: Purchase) : Int
    fun removePurchaseById(id : Int) : Int
    fun findPurchaseByNumber(purchaseNumber : Int) : Purchase?
    fun findPurchaseById(id : Int) : Purchase?
    fun getAllPurchase() : List<Purchase>
    fun findAllPurchasesByCustomer(customer: Customer): List<Purchase>
    fun findAllPurchasesByProduct(product: Product?): List<Purchase>

}