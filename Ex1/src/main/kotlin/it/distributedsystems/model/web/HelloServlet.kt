package it.distributedsystems.model.web

import it.distributedsystems.model.ejb.HelloBean
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

@WebServlet(name = "HelloServlet", urlPatterns = ["hello"], loadOnStartup = 1)
class HelloServlet : HttpServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.writer.print("Hello, World!");
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val ejb = HelloBean()
        val name = req.getParameter("name") ?: "World"
        val out = resp.writer
        out.println("Hello ${name}!!!")
        out.println(ejb.toString())
    }

}