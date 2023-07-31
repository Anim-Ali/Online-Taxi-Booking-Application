/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Booking {
    int BookingID;
    String PickupAddress;
    Timestamp DateAndTime;
    String DestinationAddress;
    double BookingCharge;
    String CustomerName;
    String DriverName;
    String VehicleNo;
    boolean BookingStatus;
    String RejectStatus;

    public Booking(String PickupAddress, Timestamp DateAndTime, String DestinationAddress, double BookingCharge, String CustomerName) {
        this.PickupAddress = PickupAddress;
        this.DateAndTime = DateAndTime;
        this.DestinationAddress = DestinationAddress;
        this.BookingCharge = BookingCharge;
        this.CustomerName = CustomerName;
    }

    
    public Booking(int BookingID, String PickupAddress, Timestamp DateAndTime, String DestinationAddress, double BookingCharge, String CustomerName, String DriverName, String VehicleNo, boolean BookingStatus, String RejectStatus) {
        this.BookingID = BookingID;
        this.PickupAddress = PickupAddress;
        this.DateAndTime = DateAndTime;
        this.DestinationAddress = DestinationAddress;
        this.BookingCharge = BookingCharge;
        this.CustomerName = CustomerName;
        this.DriverName = DriverName;
        this.VehicleNo = VehicleNo;
        this.BookingStatus = BookingStatus;
        this.RejectStatus = RejectStatus;
    }

    public Booking() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    public String getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(String PickupAddress) {
        this.PickupAddress = PickupAddress;
    }

    public Timestamp getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(Timestamp DateAndTime) {
        this.DateAndTime = DateAndTime;
    }

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String DestinationAddress) {
        this.DestinationAddress = DestinationAddress;
    }

    public double getBookingCharge() {
        return BookingCharge;
    }

    public void setBookingCharge(double BookingCharge) {
        this.BookingCharge = BookingCharge;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String DriverName) {
        this.DriverName = DriverName;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String VehicleNo) {
        this.VehicleNo = VehicleNo;
    }

    public boolean isBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(boolean BookingStatus) {
        this.BookingStatus = BookingStatus;
    }

    public String getRejectStatus() {
        return RejectStatus;
    }

    public void setRejectStatus(String RejectStatus) {
        this.RejectStatus = RejectStatus;
    }
    
    public static Booking convertToBooking(String[] values) {
        Booking result = new Booking (parseInt(values[0]), values[1], Timestamp.valueOf(values[2]),
                        values[3], parseDouble(values[4]), values[5], values[6], 
                        values[7], parseBoolean(values[8]), values[9]);
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" + "BookingID=" + BookingID + ", PickupAddress=" + PickupAddress + ", DateAndTime=" + DateAndTime + ", DestinationAddress=" + DestinationAddress + ", BookingCharge=" + BookingCharge + ", CustomerName=" + CustomerName + ", DriverName=" + DriverName + ", VehicleNo=" + VehicleNo + ", BookingStatus=" + BookingStatus + ", RejectStatus=" + RejectStatus + '}';
    }

}
