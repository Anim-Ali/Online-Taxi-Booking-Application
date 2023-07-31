/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;

/**
 *
 * @author Admin
 */
public class LoginAndRegistration {
    
    DBBean db = new DBBean();
    ResultSet resultSet = null;
    
    /* 
    Creates a new customer and returns a User object if username is not in Database
    Returns null if username is already in Database or if an error occured
    */
    public User RegisterCustomer(String username, String password, Connection con) {
        String query = "SELECT Customers.*, Drivers.DriverName, Drivers.Password, AdminTable.* FROM Customers, Drivers, AdminTable " +
                       "WHERE Customers.CustomerName = Upper('" + username + "') OR Drivers.DriverName = Upper('" + username + "') " + 
                       "OR AdminTable.AdminName = Upper('" + username + "')";
        resultSet = db.doQuery(query, con);
        try {
            // if resultSet is empty add username and password to db and return the newly created User
            if (resultSet.next() == false) {
                query =  "INSERT INTO Customers (CustomerName, Password) VALUES (Upper('" + username + "'), '" + password + "')";
                db.doQuery(query, con);
                Customer loggedUser = new Customer(username, password, con);
                return loggedUser;
            }
            // username is already taken. therefore return null
            else {
                return null;
            }
        }
        // return null in case of an SQL exception
        catch (SQLException s){
            return null;
            //s.printStackTrace();
        }
    }
    
    /* 
    Creates a new driver and returns a User object if username is not in Database
    Returns null if username is already in Database or if an error occured
    */
    public User RegisterDriver(String username,String password, String VehicleNo, Connection con) {
        String query = "SELECT Customers.*, Drivers.DriverName, Drivers.Password, AdminTable.* FROM Customers, Drivers, AdminTable " +
                       "WHERE Customers.CustomerName = Upper('" + username + "') OR Drivers.DriverName = Upper('" + username + "') " + 
                       "OR AdminTable.AdminName = Upper('" + username + "')";
        resultSet = db.doQuery(query, con);
        try {
            // if resultSet is empty add username and password to db and return the newly created User
            if (resultSet.next() == false) {
                query =  "INSERT INTO Drivers (DriverName, Password, VehicleNo, ActiveStatus) " +
                         "VALUES (Upper('" + username + "'), '" + password + "', Upper('" + VehicleNo + "'),true)";
                db.doQuery(query, con);
                Driver loggedUser = new Driver(VehicleNo, username, password, con);
                return loggedUser;
            }
            // username is already taken. therefore return null
            else {
                return null;
            }
        }
        // return null in case of an SQL exception
        catch (SQLException s){
            return null;
            //s.printStackTrace();
        }
    }
    
    /*
    Returns a User object if the credentials match with a User in DB
    Returns null if user is not found, password mismatches or if an error occurs
    */
    public User Login(String username,String password, Connection con) {
        String query = "select CustomerName, Password from Customers where CustomerName=Upper('" + username + "')";
        resultSet = db.doQuery(query, con);
        try {
            // if user not in customers table, then check drivers table
            if (resultSet.next() == false) {
                query = "select DriverName, Password, VehicleNo from Drivers where DriverName=Upper('" + username + "')and ActiveStatus=true";
                resultSet = db.doQuery(query, con);
                // if user not in drivers table, then check admin table
                if (resultSet.next() == false) {
                    query = "select AdminName, Password from AdminTable where AdminName=Upper('" + username + "')";
                    resultSet = db.doQuery(query, con);
                    // if user in admin table check credentials
                    if (resultSet.next() != false) {
                        if (username.equalsIgnoreCase(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                            Admin loggedUser = new Admin(resultSet.getString(1), resultSet.getString(2), con);
                            return loggedUser;
                        }
                    }
                }   
                // user in drivers table. check credentials
                else {
                    if (username.equalsIgnoreCase(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                    Driver loggedUser = new Driver(resultSet.getString(3), resultSet.getString(1), resultSet.getString(2), con);
                    return loggedUser;
                    }
                }
            }
            // user in customers table. check credentials
            else {
                if (username.equalsIgnoreCase(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                    Customer loggedUser = new Customer(resultSet.getString(1), resultSet.getString(2), con);
                    return loggedUser;
                }
            }
        }
        // return null in case of an SQL exception
        catch (SQLException s){
            return null;
            //s.printStackTrace();
        }
        return null;
    }
}