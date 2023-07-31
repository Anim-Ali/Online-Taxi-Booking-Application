/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Double.parseDouble;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Booking;
import model.Customer;
import model.DBBean;

/**
 *
 * @author salaam
 */
public class CustomerServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession(false);
        
        if(session == null){
            request.setAttribute("Error", "Session expired! Login please");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("name", session.getAttribute("user"));
            
        }
        
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
        request.getRequestDispatcher("customer.jsp").forward(request, response);
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
        
        // Get all user inputs
        String dateAndTime = request.getParameter("datetime");
        String pickUp = request.getParameter("pickup");
        String dropOff = request.getParameter("dropoff");
        String totalDistance = request.getParameter("tdist");
        double distance = parseDouble(totalDistance.substring(0, totalDistance.indexOf(" ")));
        dateAndTime = dateAndTime.replace('T', ' ').concat(":00");
        double gst = 0.00;
        
        double price = 0.00;
        double tPrice = 0.00;
        
        // Calculate price before showing confirm jsp
        price = Customer.calculateBookingCharge(distance);
        gst = price * 0.06;
        tPrice = price + gst;
        
        // Add all inputs to session before showing confirm page
        HttpSession session = request.getSession(false);
        session.setAttribute("pick", pickUp);
        session.setAttribute("drop", dropOff);
        session.setAttribute("distance", distance);
        session.setAttribute("datetime", dateAndTime);
        session.setAttribute("price", price);
        session.setAttribute("gst", gst);
        session.setAttribute("tprice", tPrice);
        
        
        request.getRequestDispatcher("bookingConfirm.jsp").forward(request, response);
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
