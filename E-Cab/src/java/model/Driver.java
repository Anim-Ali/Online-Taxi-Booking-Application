/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Driver extends User{
    
    String VehicleNo;
    private static DBBean db = new DBBean();
    private static ResultSet resultSet = null;

    public Driver() {
    }
    
    public Driver(String VehicleNo, String username, String password, Connection con) {
        super(username, password, con);
        this.VehicleNo = VehicleNo;
    }
    
    public Driver(String VehicleNo, String username, String password) {
        super(username, password);
        this.VehicleNo = VehicleNo;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String VehicleNo) {
        this.VehicleNo = VehicleNo;
    }
    
    // Returns true if successful, false if unsucessful
    public static boolean completeBooking(int BookingID, Connection con) {
        try {
            // Update booking
            String query = "UPDATE Bookings SET BOOKINGSTATUS = true WHERE BookingID = " + BookingID;
            resultSet = db.doQuery(query, con);
            
            // Return true when booking has been updated
            return true;
                
        // return false in case of sql exception
        } catch(Exception e){
            System.out.println("ERROR "+ e.getMessage());
            return false;
        }  
    }
    
    public static void getJobsForToday(String driverName, ArrayList<Booking> list, Connection con){
        
        try {
            String query = "select * from bookings where date(dateandtime)='"+java.time.LocalDate.now()+"' and drivername=upper('"+driverName+"')"
                    + " and bookingstatus=false";
            resultSet = db.doQuery(query, con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            
            while (resultSet.next()) {
                
                String[] values = new String[columnsNumber]; 
                for (int i = 1; i <= columnsNumber; i++) {
                    values[i-1] = resultSet.getString(i);
                }
                
                list.add(Booking.convertToBooking(values));
            }
        } catch (SQLException s){
            System.out.println("Error: " + s.getMessage());
        }
    }
    
    //Updates an array list with list of jobs for the driver
    public static void getJobList(String DriverName, ArrayList<Booking> list,Connection con) {
        //Get List of Jobs From Database
        try {
            String query = "select * from bookings where DRIVERNAME=Upper('" + DriverName + "') and bookingstatus=false";
            resultSet = db.doQuery(query, con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            // Go through each entry in resultSet
            while (resultSet.next()) {
                // For each entry store values in values array
                String[] values = new String[columnsNumber]; 
                for (int i = 1; i <= columnsNumber; i++) {
                    values[i-1] = resultSet.getString(i);
                }
                //Convert values in values in value array to an object and add the object to list
                list.add(Booking.convertToBooking(values));
            }
        } catch (SQLException s){
            System.out.println("Error: " + s.getMessage());
        }
    }
    
    public static String getVehicleNoOfDriver(String driver, Connection con){
        String vehicleno = null;
        String query = "select vehicleno from drivers where drivername='" + driver.toUpperCase() + "'";
        try {
            resultSet = db.doQuery(query, con);
            while(resultSet.next()){
                vehicleno = resultSet.getString("VEHICLENO");
            }
        } catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        
        return vehicleno;
    }
    
    public static Driver convertToDriver(String[] values) {
        Driver result = new Driver (values[2], values[0], values[1]);
        return result;
    }
    
    @Override
    public String toString() {
        return "username=" + this.getUsername() + ", password=" + this.getPassword() + ", VehicleNo=" + VehicleNo;
    }
    
}
