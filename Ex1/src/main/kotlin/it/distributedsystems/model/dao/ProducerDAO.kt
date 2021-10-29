package it.distributedsystems.model.dao

interface ProducerDAO {

    fun insertProducer(producer : Producer) : Int
    fun removeProducerByName(name : String) : Int
    fun removeProducerById(id : Int) : Int
    fun findProducerById(id : Int) : Producer?
    fun findProducerByName(name : String) : Producer?
    fun getAllProducers() : List<Producer>

}