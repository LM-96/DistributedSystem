package it.distributedsystems.util

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateSessionFactory {

    @JvmStatic val sessionFactory: SessionFactory =
        try {
            Configuration().configure().buildSessionFactory()
        } catch (e : Throwable) {
            System.err.println("Initial SessionFactory creation failed.${e}")
            throw ExceptionInInitializerError(e)
        }

    @JvmStatic fun shutdown() {
        sessionFactory.close()
    }

}