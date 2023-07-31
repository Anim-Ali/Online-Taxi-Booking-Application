/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@WebService(serviceName = "BookingChargesCalculatorWS")
@Stateless()
public class BookingChargesCalculatorWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CalculateBookingCharge")
    public double CalculateBookingCharge(@WebParam(name = "Distance") double Distance) {
        //TODO write your implementation code here:
        double bookingCharge = Distance * 25;
        return bookingCharge;
    }
    
}
