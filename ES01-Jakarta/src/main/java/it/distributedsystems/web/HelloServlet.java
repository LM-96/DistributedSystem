package it.distributedsystems.web;

import it.distributedsystems.model.ejb.HelloBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = {"hello"}, loadOnStartup = 1)
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Hello, World!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HelloBean ejb = new HelloBean();
        String name = request.getParameter("name");
        if (name == null) name = "World";
        PrintWriter out = response.getWriter();
        out.println("Hello "+name+"!!!");
        out.println(ejb.toString());
    }
}