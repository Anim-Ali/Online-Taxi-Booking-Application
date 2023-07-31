/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import model.Customer;
import model.Driver;

/**
 *
 * @author Admin
 */
public class adminReports extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            //request.getRequestDispatcher("admin.jsp").forward(request, response);
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
        Gson gson = new Gson(); 
        JsonElement element = null;
        String buttonID = request.getParameter("button-id");
        ArrayList<Booking> bookingList;
        
        switch (buttonID) {
            case "bookings":
                bookingList = Admin.getAllBookings(con);
                element = gson.toJsonTree(bookingList, new TypeToken<List<Booking>>() {}.getType());
                break;
                 
            case "customers":
                ArrayList<Object> customerList = Admin.getCustomerStatistics(con);
                element = gson.toJsonTree(customerList, new TypeToken<List<Object>>() {}.getType());
                break;
                
            case "drivers":
                ArrayList<Object> driverList = Admin.getDriverStatistics(con);
                element = gson.toJsonTree(driverList, new TypeToken<List<Object>>() {}.getType());
                break;
                
            case "dailyDrivers":
                bookingList = Admin.getBookingsCompletedByDriverToday(request.getParameter("driver"), con);
                element = gson.toJsonTree(bookingList, new TypeToken<List<Booking>>() {}.getType());
                break;
            
            case "dailyBookings":
                bookingList = Admin.getTodaysBookings(con);
                element = gson.toJsonTree(bookingList, new TypeToken<List<Booking>>() {}.getType());
                break;
        }
        
        JsonArray jsonArray = element.getAsJsonArray(); 
        log("success");
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
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
