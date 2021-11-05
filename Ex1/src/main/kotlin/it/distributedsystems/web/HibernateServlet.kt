package it.distributedsystems.web

import it.distributedsystems.util.HibernateSessionFactory.sessionFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import kotlin.Throws
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import it.distributedsystems.model.ejb.Department
import it.distributedsystems.model.ejb.Employee

@WebServlet(name = "HibernateServlet", urlPatterns = ["hibernate"], loadOnStartup = 1) // TODO: demo/hibernate???
class HibernateServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        initSessionFactory(response)
    }

    @Throws(IOException::class)
    private fun initSessionFactory(response: HttpServletResponse) {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        val department = Department(name = "java")
        session.save(department)
        session.save(Employee(name = "Jakab Gipsz", department = department))
        session.save(Employee(name = "Captain Nemo", department = department))
        session.transaction.commit()
        val q = session.createQuery("From Employee ")
        val out = response.writer
        val resultList = q.list() as List<Employee>
        println("num of employess:" + resultList.size)
        out.println("num of employees:" + resultList.size)
        for (next in resultList) {
            println("next employee: $next")
            out.println(next.toString())
        }

        /*
        session.disconnect();
        session.close();
        HibernateUtil.shutdown();
        */
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        initSessionFactory(response)
    }
}