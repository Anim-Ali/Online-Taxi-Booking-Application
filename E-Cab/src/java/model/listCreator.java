/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class listCreator {
    
    private static DBBean db = new DBBean();
    private static ResultSet resultSet = null;
    
    //Booking --> Type1 Object ... Customers ---> Type2 Object ... Drivers ---> Type3 Object
    public static void populateList(int type, String query, ArrayList<Object> list, Connection con) {
        // Get Query Results
        try {
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
                if (type == 1) {
                    list.add(Booking.convertToBooking(values));
                }
                else if (type == 2) {
                    list.add(Customer.convertToCustomer(values));
                }
                else if (type == 3) {
                    list.add(Driver.convertToDriver(values));
                }
            }
        } catch (SQLException s){
            System.out.println("Error: " + s.getMessage());
        }
    }
}
