package it.distributedsystems.model.ejb;

import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.distributedsystems.model.dao.*;

public class EJB3DaoFactory extends DAOFactory {
    private static Logger logger = LogManager.getLogger("DAOFactory");//Logger.getLogger("DAOFactory");

    public EJB3DaoFactory() {

    }

    private static InitialContext getInitialContext() throws Exception {
        return new InitialContext();
    }

    public CustomerDAO getCustomerDAO() {
        try {
            InitialContext context = getInitialContext();
            CustomerDAO result = (CustomerDAO)context.lookup("java:module/EJB3CustomerDAO");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3CustomerDAO", var3);
            return null;
        }
    }

    public PurchaseDAO getPurchaseDAO() {
        try {
            InitialContext context = getInitialContext();
            PurchaseDAO result = (PurchaseDAO)context.lookup("java:module/EJB3PurchaseDAO");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3PurchaseDAO", var3);
            return null;
        }
    }

    public ProductDAO getProductDAO() {
        try {
            InitialContext context = getInitialContext();
            ProductDAO result = (ProductDAO)context.lookup("java:module/EJB3ProductDAO");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3ProductDAO", var3);
            return null;
        }
    }

    public ProducerDAO getProducerDAO() {
        try {
            InitialContext context = getInitialContext();
            ProducerDAO result = (ProducerDAO)context.lookup("java:module/EJB3ProducerDAO");
            return result;
        } catch (Exception var3) {
            logger.error("Error looking up EJB3ProducerDAO", var3);
            return null;
        }
    }
}
