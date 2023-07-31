/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 *
 * @author Admin
 */
public class Admin extends User {
    
    private static DBBean db = new DBBean();
    private static ResultSet resultSet = null;
    
    public Admin(String username, String password, Connection con) {
        super(username, password, con);
    }
    
    // Return turnover if successful, return -1.0 if unsuccessful
    // Currently Full charge amount goes to Driver... TO be changed if eCab takes a percentage
    public static Double calculateTurnover(String DriverName, Connection con) {
        String query = "SELECT SUM (BOOKINGCHARGE) FROM bookings where (DRIVERNAME = Upper('" + DriverName + "')" + 
                        " and  bookingstatus = true and DATE(DATEANDTIME) = '" + java.time.LocalDate.now() + "')";
        
        try {
            //Get drivers turnover from db
            resultSet = db.doQuery(query, con);
            resultSet.next();
            double turnover = Double.valueOf(resultSet.getString(1));
            // return turnover
            return turnover;
                
        // return -1 in case of sql exception
        } catch(Exception e){
            System.out.println("ERROR "+ e.getMessage());
            return -1.0;
        }
    }
    
    /******* HISTORICAL DATA SECTION *********/
    
    public static void getAllCustomers(ArrayList<Object> list, Connection con) {
        String query = "select * from customers";
        listCreator.populateList(2, query, list, con);
    }
    
    public static void getAllDrivers(ArrayList<Object> list, Connection con) {
        String query = "select * from Drivers where ActiveStatus=true";
        listCreator.populateList(3, query, list, con);
    }
    
    public static void getAllInactiveDrivers(ArrayList<Object> list, Connection con) {
        String query = "select * from Drivers where ActiveStatus=false";
        listCreator.populateList(3, query, list, con);
    }
    
    public static void getAllBookings(ArrayList<Object> list, Connection con){
        String query = "select * from bookings";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getUnassignedBookings(ArrayList<Object> list, Connection con) {
        String query = "select * from bookings where Drivername IS NULL and rejectstatus is null";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getAllRejectedBookings(ArrayList<Object> list, Connection con){
        String query = "SELECT * FROM bookings where rejectstatus is not null and drivername is NULL";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getTodaysBookings(ArrayList<Object> list, Connection con) {
        String query = "SELECT * FROM bookings WHERE DATE(DATEANDTIME) = '" + java.time.LocalDate.now() + "'";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getBookingsCompletedByDriver(String DriverName, ArrayList<Object> list, Connection con) {
        String query = "select * from bookings where (DRIVERNAME = Upper('" + DriverName + "') and  bookingstatus = true)";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getBookingsCompletedByDriverToday(String DriverName, ArrayList<Object> list, Connection con) {
        String query = "select * from bookings where (DRIVERNAME = Upper('" + DriverName + "')" + 
                        " and  bookingstatus = true and DATE(DATEANDTIME) = '" + java.time.LocalDate.now() + "')";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void getDriverInfo (String DriverName, ArrayList<Object> list, Connection con) {
        String query = "select * from DRIVERS where DRIVERNAME = Upper('" + DriverName + "')and ActiveStatus=true";
        listCreator.populateList(3, query, list, con);
    }
    
    public static void getInactiveDriverInfo (String DriverName, ArrayList<Object> list, Connection con) {
        String query = "select * from DRIVERS where DRIVERNAME = Upper('" + DriverName + "')and ActiveStatus=false";
        listCreator.populateList(3, query, list, con);
    }
    
    public static void getBookingByID (int BookingID, ArrayList<Object> list, Connection con) {
        String query = "select * from BOOKINGS where BOOKINGID = ("+ BookingID +")";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void removeDriver(String DriverName, Connection con){
        //String query = "delete from DRIVERS where driverName='"+name+"'";
        String query = "UPDATE DRIVERS SET ActiveStatus=false where Drivername = Upper('" + DriverName + "')";
        db.doQuery(query, con);
    }
    
    public static void removeDriverFromBooking(String DriverName, Connection con){
        //String query = "delete from DRIVERS where driverName='"+name+"'";
        String query = "UPDATE BOOKINGS SET Drivername =null, Vehicleno=null where Drivername = Upper('" + DriverName + "') and BookingStatus=false";
        db.doQuery(query, con);
    }
    
    public static void assignBooking (int bookingID, String DriverName, String VehicleNo, Connection con) {
        String query = "UPDATE BOOKINGS SET Drivername = Upper('" + DriverName + "'), Vehicleno = ('" + VehicleNo + "'), "
                + "BookingStatus = false WHERE bookingid = (" + bookingID + ")";
        db.doQuery(query, con);
    }
    
    public static void getAssignedBookings (String DriverName, ArrayList<Object> list, Connection con){
        String query = "select * from bookings where (DRIVERNAME = Upper('" + DriverName + "') and  bookingstatus = false)";
        listCreator.populateList(1, query, list, con);
    }
    
    public static void rejectBooking (int bookingID, String DriverName, String RejectStatus, Connection con){
        String query = "UPDATE BOOKINGS SET Drivername = NULL, BookingStatus = NULL, Vehicleno = NULL, "
                + "RejectStatus = ('" + DriverName + " - " + RejectStatus + "') WHERE bookingid = (" + bookingID + ")";
        db.doQuery(query, con);
    }
    
    public static String reAssignBooking (int bookingID, String DriverName, String VehicleNo, Connection con) throws SQLException{
        String query = "select * from bookings where bookingID = (" + bookingID + ")";
        resultSet = db.doQuery(query, con);
            
            
            resultSet.next();
            String rejectStatus = resultSet.getString("REJECTSTATUS");
            //String[] arr = rejectStatus.split("\\s+");
            
            int i = rejectStatus.indexOf(' ');
            String rejectedDriver = rejectStatus.substring(0, i);
            
            if(rejectedDriver != DriverName){
                String query2 = "UPDATE BOOKINGS SET Drivername = Upper('" + DriverName + "'), Vehicleno = ('" + VehicleNo + "'), "
                        + "RejectStatus = NULL, Bookingstatus = false where bookingid = (" + bookingID + ")";
                db.doQuery(query2, con);
                return "Driver Assigned";
            }else{
                return "Driver cannot be assigned";
            }
    }
    
    public static String booking2(int bookingID, Connection con) throws SQLException{
        String query = "select * from bookings where bookingID = (" + bookingID + ")";
        resultSet = db.doQuery(query, con);
            
            
            resultSet.next();
            String rejectStatus = resultSet.getString("REJECTSTATUS");
            //String[] arr = rejectStatus.split("\\s+");
            
            int i = rejectStatus.indexOf(' ');
            String rejectedDriver = rejectStatus.substring(0, i);
            
            return rejectedDriver;
    }
    // version 2
    public static ArrayList<Customer> getAllCustomers(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from customers";
        listCreator.populateList(2, query, list, con);
        ArrayList<Customer> newList = (ArrayList<Customer>) (Object) list;
        return newList;
    }
    
    // version 2
    public static ArrayList<Driver> getAllDrivers(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from Drivers";
        listCreator.populateList(3, query, list, con);
        ArrayList<Driver> newList = (ArrayList<Driver>) (Object) list;
        return newList;
    }
    
    // version 2
    public static ArrayList<Booking> getAllBookings(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "SELECT * FROM bookings";
        listCreator.populateList(1, query, list, con);
        ArrayList<Booking> newList = (ArrayList<Booking>) (Object) list;
        return newList;
    }
    
    // version 2
    public static ArrayList<Booking> getTodaysBookings(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "SELECT * FROM bookings WHERE DATE(DATEANDTIME) = '" + java.time.LocalDate.now() + "'";
        listCreator.populateList(1, query, list, con);
        ArrayList<Booking> newList = (ArrayList<Booking>) (Object) list;
        return newList;
    }
    
    // version 2
    public static ArrayList<Booking> getBookingsCompletedByDriverToday(String DriverName,Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from bookings where (DRIVERNAME = Upper('" + DriverName + "')" + 
                        " and  bookingstatus = true and DATE(DATEANDTIME) = '" + java.time.LocalDate.now() + "')";
        listCreator.populateList(1, query, list, con);
        ArrayList<Booking> newList = (ArrayList<Booking>) (Object) list;
        return newList;
    }
    
    // returns an object array of customer name, total bookings requested and total customer charges
    public static ArrayList<Object> getCustomerStatistics(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from customers";
        listCreator.populateList(2, query, list, con);
        ArrayList<Customer> customerList = (ArrayList<Customer>) (Object) list;
        String name, noOfBookings = null, totalCharges = null;
        ArrayList<Object> customerStatistics = new ArrayList<Object>();
        for (int i = 0; i < customerList.size(); i++) {
            name = customerList.get(i).getUsername();
            try {
                query = "SELECT COUNT(bookingid) FROM bookings WHERE customername = upper('" + name + "')";
                resultSet = db.doQuery(query, con);
                resultSet.next();
                noOfBookings = resultSet.getString(1);
                query = "SELECT SUM(bookingcharge) FROM bookings WHERE (customername = upper('" + name + "') and bookingstatus = true)";
                resultSet = db.doQuery(query, con);
                resultSet.next();
                totalCharges = resultSet.getString(1);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (totalCharges == null) {
                totalCharges = "0";
            }
            Data temp = new Data(name, noOfBookings, totalCharges);
            customerStatistics.add(temp);
        }
        return customerStatistics;
    }   
    
    // returns an object array of driver name, vehicle no, assigned bookings no, completed bookings no and turnover
    public static ArrayList<Object> getDriverStatistics(Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from drivers";
        listCreator.populateList(3, query, list, con);
        ArrayList<Driver> driverList = (ArrayList<Driver>) (Object) list;
        String name, completeBookingsNo = null, totalBookingsNo = null, totalCharges = null;
        ArrayList<Object> driverStatistics = new ArrayList<Object>();
        for (int i = 0; i < driverList.size(); i++) {
            name = driverList.get(i).getUsername();
            try {
                query = "SELECT COUNT(bookingid) FROM bookings WHERE drivername = upper('" + name + "')";
                resultSet = db.doQuery(query, con);
                resultSet.next();
                totalBookingsNo = resultSet.getString(1);
                query = "SELECT SUM(bookingcharge) FROM bookings WHERE (drivername = upper('" + name + "') and bookingstatus = true)";
                resultSet = db.doQuery(query, con);
                resultSet.next();
                totalCharges = resultSet.getString(1);
                query = "SELECT COUNT(bookingid) FROM bookings WHERE (drivername = upper('" + name + "') and bookingstatus = true)";
                resultSet = db.doQuery(query, con);
                resultSet.next();
                completeBookingsNo = resultSet.getString(1);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (totalCharges == null) {
                totalCharges = "0";
            }
            Data temp = new Data(name, driverList.get(i).getVehicleNo(), totalBookingsNo, completeBookingsNo, totalCharges);
            driverStatistics.add(temp);
        }
        return driverStatistics;
    }   
}
