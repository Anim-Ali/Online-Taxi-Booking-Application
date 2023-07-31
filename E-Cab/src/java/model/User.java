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
public abstract class User {
     
    private String username;
    String password;
    Connection con;

    public User(){
        
    }
    
    public User(String username, String password, Connection con) {
        this.username = username;
        this.password = password;
        this.con = con;
    }
    
    public User(String name, String password){
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    @Override
    public String toString() {
        return "username=" + username + ", password=" + password ;
    }
}

