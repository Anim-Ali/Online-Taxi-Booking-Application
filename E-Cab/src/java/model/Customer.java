/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Customer extends User {

    // Static variables
    private static DBBean db = new DBBean();
    private static ResultSet resultSet = null;

    
    // Empty Constructor
    public Customer() {

    }

    public Customer(String username, String password, Connection con) {
        super(username, password, con);
    }
    
    // Constructor without connection
    public Customer(String name, String password){
        super(name, password);
    }

    
    // These are all static methods
    
    // calculate charges for the booking using web service
    public static double calculateBookingCharge(double distance) {
        org.me.calculator.BookingChargesCalculatorWS_Service service = new org.me.calculator.BookingChargesCalculatorWS_Service();
        org.me.calculator.BookingChargesCalculatorWS port = service.getBookingChargesCalculatorWSPort();
        return port.calculateBookingCharge(distance);
    }

    // Parameters --> String: PickupAddress, String: DateAndTime, String: DestinationAddress, String: CustomerName, Double: distance, Connection: con
    // returns booking object if successful, returns null if unsuccessful
    public static Booking createBooking(String PickupAddress, String DateAndTime, String DestinationAddress, String CustomerName, double price, Connection con) {
        // Calculate charges using web service

        try {
            // Add new booking to DB
            String query = "insert into bookings (PickupAddress,DateAndTime,DestinationAddress, BookingCharge, CustomerName) \n"
                    + "values ('" + PickupAddress + "', '" + DateAndTime + "', '" + DestinationAddress + "', " + price
                    + ", upper('" + CustomerName + "'))";
            resultSet = db.doQuery(query, con);

            Timestamp dateAndTime = Timestamp.valueOf(DateAndTime); //converting string into sql Timestamp  
            // Create and return booking object 
            Booking newBooking = new Booking(PickupAddress, dateAndTime, DestinationAddress, price, CustomerName);
            return newBooking;

            // return null in case of sql exception
        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            return null;
        }
    }
    
    public static ArrayList<Booking> getCustomerBookings(String CustomerName,Connection con) {
        ArrayList<Object> list = new ArrayList<Object>();
        String query = "select * from bookings where CUSTOMERNAME = Upper('" + CustomerName + "')";
        listCreator.populateList(1, query, list, con);
        ArrayList<Booking> newList = (ArrayList<Booking>) (Object) list;
        return newList;
    }
    
    // Returns a customer object if exists with username and password without connection
    // This method has not been used yet
    public static Customer getCustomerByName(String name, Connection con) {
        Customer customer = null;

        try {
            String query = "select * from customers where customername='" + name + "'";
            resultSet = db.doQuery(query, con);
            customer = new Customer(resultSet.getNString(1),resultSet.getString(2));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return customer;
    }
    
    public static Customer convertToCustomer(String[] values) {
        Customer result = new Customer (values[0], values[1]);
        return result;
    }
    
}
