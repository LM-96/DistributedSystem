package it.distributedsystems.model.dao

import it.distributedsystems.model.ejb.EJB3DaoFactory

abstract class DAOFactory {

    companion object {
        @JvmStatic fun getDAOFactory(whichFactory : String) : DAOFactory {
            return EJB3DaoFactory()
        }
    }

    abstract fun getCustomerDAO() : CustomerDAO?
    abstract fun getPurchaseDAO() : PurchaseDAO?
    abstract fun getProductDAO() : ProductDAO?
    abstract fun getProducerDAO() : ProducerDAO?

}