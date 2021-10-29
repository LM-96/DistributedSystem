package it.distributedsystems.model.ejb

import javax.ejb.CreateException
import javax.ejb.SessionBean
import javax.ejb.SessionContext
import kotlin.jvm.Throws

class HelloBean : SessionBean {

    fun helloWorld() : String {
        return "Hello from myEjb.HelloBean"
    }

    @Throws(CreateException::class)
    fun ejbCreate() {}

    override fun setSessionContext(ctx: SessionContext?) {}

    override fun ejbRemove() {}

    override fun ejbActivate() {}

    override fun ejbPassivate() {}
}