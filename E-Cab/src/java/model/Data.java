/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */

public class Data {
    String name;
    String vehicleNo;
    String totalBookingsNo;
    String completeBookingsNo;
    String totalCharges;

    public Data(String name, String vehicleNo, String totalBookingsNo, String completeBookingsNo, String totalCharges) {
        this.name = name;
        this.vehicleNo = vehicleNo;
        this.totalBookingsNo = totalBookingsNo;
        this.completeBookingsNo = completeBookingsNo;
        this.totalCharges = totalCharges;
    }
    
    public Data(String name, String totalBookingsNo, String totalCharges) {
        this.name = name;
        this.totalBookingsNo = totalBookingsNo;
        this.totalCharges = totalCharges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalBookingsNo() {
        return totalBookingsNo;
    }

    public void setTotalBookingsNo(String totalBookingsNo) {
        this.totalBookingsNo = totalBookingsNo;
    }

    public String getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(String totalCharges) {
        this.totalCharges = totalCharges;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleno) {
        this.vehicleNo = vehicleno;
    }

    public String getCompleteBookingsNo() {
        return completeBookingsNo;
    }

    public void setCompleteBookingsNo(String completeBookingsNo) {
        this.completeBookingsNo = completeBookingsNo;
    }
    
}
