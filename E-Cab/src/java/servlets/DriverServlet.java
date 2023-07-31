/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
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
public class DriverServlet extends HttpServlet {

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
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        ArrayList<Booking> jobsToday = new ArrayList<Booking>();
        
        String driverName = request.getSession(false).getAttribute("user").toString();
        
        Driver.getJobList(driverName, bookings, con);
        
        Driver.getJobsForToday(driverName, jobsToday, con);
        
        request.getSession(false).setAttribute("assignedJobs", bookings);
        request.getSession(false).setAttribute("todaysjobs", jobsToday);
        
        request.getRequestDispatcher("driver.jsp").forward(request, response);
        
        
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
        Connection con = (Connection) getServletContext().getAttribute("connection");
        String[] bookingIDs = request.getParameterValues("id");
        String[] bookingStatus = request.getParameterValues("status");
        String[] rejectComment = request.getParameterValues("comment");
        
        PrintWriter out = response.getWriter();
        for(int i= 0; i < bookingIDs.length; i++){
            if(bookingStatus[i].equalsIgnoreCase("Completed")){
                Driver.completeBooking(Integer.parseInt(bookingIDs[i]), con);
            } else if(bookingStatus[i].equalsIgnoreCase("reject")){
                Admin.rejectBooking(Integer.parseInt(bookingIDs[i]), request.getSession().getAttribute("user").toString().toUpperCase(), rejectComment[i], con);
            }
            
        }
        
        response.sendRedirect("DriverServlet");
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
