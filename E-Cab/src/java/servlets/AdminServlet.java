/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.Booking;
import model.Driver;

/**
 *
 * @author salaam
 */
public class AdminServlet extends HttpServlet {

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
        Connection con = (Connection)getServletContext().getAttribute("connection");
        
        ArrayList<Object> newBookings = new ArrayList<Object>();
        ArrayList<Object> rBookings = new ArrayList<Object>();
        ArrayList<Object> allBookings = new ArrayList<Object>();
        ArrayList<Object> allDrivers = new ArrayList<Object>();
        
        Admin.getUnassignedBookings(newBookings, con);
        Admin.getAllRejectedBookings(rBookings, con);
        Admin.getAllBookings(allBookings, con);
        Admin.getAllDrivers(allDrivers, con);
        
        HttpSession session = request.getSession(false);
        session.setAttribute("newBookings", newBookings);
        session.setAttribute("rejectedBookings", rBookings);
        session.setAttribute("allBookings", allBookings);
        session.setAttribute("allDrivers",allDrivers);
        
        request.getRequestDispatcher("admin.jsp").forward(request, response);
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
//        Connection con = (Connection)getServletContext().getAttribute("connection");
//        String[] driversAssigned = request.getParameterValues("drivername");
//        String[] bookingIds = request.getParameterValues("id");
//        
//        for(int i = 0; i < driversAssigned.length; i++){
//            if(!driversAssigned[i].equals("Unassigned")){
//                
//                int id = Integer.parseInt(bookingIds[i]);
//                
//                // Need to add correct vehicel no
//                Admin.assignBooking(id, driversAssigned[i], "P0000", con);
//            }
//        }
//        
//        response.sendRedirect("AdminServlet");
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
    
    protected void customLog(Object Output) {
        Logger log = Logger.getLogger("my.logger");
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        log.addHandler(handler);
        log.fine("Output: " + Output);
    }
}
