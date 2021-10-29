package it.distributedsystems.model.ejb

import it.distributedsystems.model.dao.*
import org.apache.log4j.Logger
import java.util.Hashtable
import javax.naming.InitialContext
import kotlin.jvm.Throws

class EJB3DaoFactory : DAOFactory() {

    companion object {
        @JvmStatic val logger = Logger.getLogger("DAOFactory")

        @JvmStatic
        @Throws(Exception::class)
        fun getInitialContext() : InitialContext {
            return InitialContext(getInitialContextProperties())
        }

        @JvmStatic
        fun getInitialContextProperties() : Hashtable<String, String> {
            val props = Hashtable<String, String>()
            props["java.naming.factory.initial"] = "org.jnp.interfaces.NamingContextFactory"
            props["java.naming.factory.url.pkgs"] = "org.jboss.naming:org.jnp.interfaces"
            props["java.naming.provider.url"] = "127.0.0.1:1099" //(new ServerInfo()).getHostAddress()  --- 127.0.0.1 --
            return props;
        }
    }

    override fun getCustomerDAO(): CustomerDAO? {
        return try {
            val context = getInitialContext()
            context.lookup("distributed-systems-demo/EJB3CustomerDAO/local") as CustomerDAO
        } catch (e : Exception) {
            logger.error("Error looking up EJB3CustomerDAO", e)
            null
        }
    }

    override fun getPurchaseDAO(): PurchaseDAO? {
        return try {
            val context = getInitialContext()
            context.lookup("distributed-systems-demo/EJB3PurchaseDAO/local") as PurchaseDAO
        } catch (e : Exception) {
            logger.error("Error looking up EJB3PurchaseDAO", e)
            null
        }
    }

    override fun getProductDAO(): ProductDAO? {
        return try {
            val context = getInitialContext()
            context.lookup("distributed-systems-demo/EJB3ProductDAO/local") as ProductDAO
        } catch (e : Exception) {
            logger.error("Error looking up EJB3ProductDAO", e)
            null
        }
    }

    override fun getProducerDAO(): ProducerDAO? {
        return try {
            val context = getInitialContext()
            context.lookup("distributed-systems-demo/EJB3ProducerDAO/local") as ProducerDAO
        } catch (e : Exception) {
            logger.error("Error looking up EJB3ProducerDAO", e)
            null
        }
    }
}