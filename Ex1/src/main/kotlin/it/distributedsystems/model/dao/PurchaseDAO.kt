package it.distributedsystems.model.dao

interface PurchaseDAO {

    fun insertPurchase(purchase: Purchase) : Int
    fun removePurchaseById(id : Int) : Int
    fun findPurchaseByNumber(purchaseNumber : Int) : Purchase?
    fun findPurchaseById(id : Int) : Purchase?
    fun getAllPurchase() : List<Purchase>
    fun findAllPurchaseByCustomer(customer: Customer) : List<Purchase>
    fun findAllPurchaseByProduct(product : Product) : List<Purchase>

}