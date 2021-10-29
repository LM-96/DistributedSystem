package it.distributedsystems.model.dao

interface CustomerDAO {

    fun insertCustomer(customer: Customer) : Int
    fun removeCustomerByName(name : String) : Int
    fun removeCustomerById(id : Int) : Int
    fun findCustomerByName(name : String) : Customer?
    fun findCustomerById(id : Int) : Customer?
    fun getAllCustomer() : List<Customer>

}