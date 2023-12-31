/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.Customer;
import model.Driver;
import model.LoginAndRegistration;
import model.User;

/**
 *
 * @author salaam
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        // Get name and password from user
        String name = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");
        
        
        // Get DB connection
        Connection conn = (Connection)getServletContext().getAttribute("connection");
        
        // Get user from DB
        LoginAndRegistration lr = new LoginAndRegistration();
        Object s = lr.Login(name, password, conn);
        
        // Session
        HttpSession session = request.getSession();
        
        try {
            if(s.getClass() == Customer.class){
                session.setAttribute("user", name);
                session.setAttribute("type", "customer");
                response.sendRedirect("CustomerServlet");
            } else if(s.getClass() == Admin.class){
                session.setAttribute("user", name);
                session.setAttribute("type", "admin");
                response.sendRedirect("AdminServlet");
            } else if(s.getClass() == Driver.class){
                session.setAttribute("user", name);
                session.setAttribute("type", "driver");
                response.sendRedirect("DriverServlet");
            }
        } catch(Exception e){
            System.out.println("Error " + e);
            request.setAttribute("Error", "Incorrect username or password! please try again!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
