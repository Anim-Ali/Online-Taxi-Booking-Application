<%-- 
    Document   : admin
    Created on : Sep 29, 2020, 4:35:23 PM
    Author     : Dhashin
--%>

<%@page import="model.Driver"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Booking"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin View</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script src="js/login.js"></script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script> 
        <script src="js/admin.js"></script>
        <script src="js/modal.js"></script>
        <script src="js/ddtf.js"></script>
    </head>
    <body>

        <section id="title">
            <div>
                <nav class="navbar navbar-expand-lg navbar-dark navbarr">
                    <img src="taxi.png" alt="" height="60">
                    <a class="navbar-brand" href="">E-CAB</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <button type="button" class="btn button-driver" id="assignPage">Assign</button>
                            </li>
                            <li class="nav-item">
                                <button type="button" class="btn button-driver" name="Login" data-toggle="modal" data-target="#registerModal">Add Driver</button>
                            </li>
                            <li class="nav-item">
                                <button type="button" class="btn button-driver" data-toggle="modal" data-target="#removeDriverModal" >Remove Driver</button>
                            </li>
                            <li class="nav-item">
                                <div class="dropdown">
                                    <button class="btn button-driver dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Reports
                                    </button>
                                    <div class="dropdown-menu dropdown-edited" aria-labelledby="dropdownMenuButton">
                                        <button type="button" class="dropdown-item dropdown-edited" id="customers">Customers</button>
                                        <button type="button" class="dropdown-item dropdown-edited" id="drivers">Drivers</button>
                                        <button type="button" class="dropdown-item dropdown-edited" id="bookings">Bookings</button>
                                    </div>
                                </div>
                            </li>
                            <li class="nav-item">
                                <!--<button type="button" class="btn btn-dark" id="logoutButton" href="LogoutServlet">Sign Out</button>-->
                                <a class="btn button-driver" href="LogoutServlet">Sign Out</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </section>


        <!-- Notifications -->        
        <div class="container mt-5 msg">
            <% String assignError = (String) session.getAttribute("Error");
                String message = (String) session.getAttribute("msg");
                if (assignError != null) {%>
            <div class="alert alert-info" role="alert">
                <%=assignError%>
            </div>
            <% } else if (message != null) {%>
            <div class="alert alert-info" role="alert">
                <%=message%>
            </div>
            <% } %>
        </div>

        <div class="container mt-5" id="assignDiv">
            <% ArrayList<Booking> newBookings = (ArrayList<Booking>) session.getAttribute("newBookings");
                ArrayList<Booking> rBookings = (ArrayList<Booking>) session.getAttribute("rejectedBookings");
                ArrayList<Booking> allBookings = (ArrayList<Booking>) session.getAttribute("allBookings");
                ArrayList<Driver> allDrivers = (ArrayList<Driver>) session.getAttribute("allDrivers");
            %>

            <div class="summary-div">
                <h3 class="summary-h3">Booking Overview</h3>
                <table class="table table-striped summary-table">
                    <tr>
                        <td>Number of Bookings</td>
                        <td><%=allBookings.size()%></td>
                    </tr>
                    <tr>
                        <td>Unassigned Bookings</td>
                        <td><%=newBookings.size()%></td>
                    </tr>
                    <tr>
                        <td>Rejected Bookings</td>
                        <td><%=rBookings.size()%></td>
                    </tr>
                </table>
            </div>


            <% if (newBookings.size() > 0) { %>
            <div class="mt-5 summary-div">
                <form action="AssignDriverServlet" method="POST">
                    <div class="">
                        <h3 class="summary-h3">Unassigned Bookings</h3>
                        <table class="table table-striped summary-table">
                            <thead class="table-head">
                            <th>ID</th>
                            <th>Pick Up</th>
                            <th>Time</th>
                            <th>Drop Off</th>
                            <th>Driver Name</th>
                            <th>Date & Time</th>
                            <th>Booking Charge</th>
                            <th>Customer Name</th>

                            </thead>
                            <% for (Booking booking : newBookings) {%>
                            <tr>
                                <td>
                                    <input name="id" class="tbl-inputs" value="<%=booking.getBookingID()%>" size="2" readonly >
                                </td>
                                <td><%=booking.getPickupAddress()%></td>
                                <td><%=booking.getDateAndTime()%></td>
                                <td><%=booking.getDestinationAddress()%></td>
                                <td>
                                    <select class="form-control form-control-sm select-box" name="drivername">
                                        <option>Unassigned </option>
                                        <% for (Driver driver : allDrivers) {%>
                                        <option><%=driver.getUsername()%></option>
                                        <%}%> 
                                    </select>
                                </td>
                                <td><%=booking.getDateAndTime()%></td>
                                <td><%=booking.getBookingCharge()%></td>
                                <td><%=booking.getCustomerName()%></td>
                            </tr>
                            <% } %>
                        </table>
                        <button type="submit" class="btn button-admin" id="assignButton">Assign Driver</button>
                    </div>
                </form>
            </div>
            <% } %>

            <% if (rBookings.size() > 0) { %>

            <div class="mt-5 summary-div">
                <h3 class="summary-h3">Rejected Bookings</h3>
                <form action="ReAssignDriverServlet" method="POST">
                    <table class="table table-striped summary-table">
                        <thead class="table-head">
                        <th>ID</th>
                        <th>Pick Up</th>
                        <th>Time</th>
                        <th>Drop Off</th>
                        <th>Booking Charge</th>
                        <th>Customer Name</th>
                        <th>Driver Name</th>
                        <th>Reject Status</th>
                        </thead>
                        <% for (Booking booking : rBookings) {%>

                        <% String status = booking.getRejectStatus();
                            String driverName = status.substring(0, status.indexOf(" "));
                        %>
                        <tr class="re-assign-row">
                            <td>
                                <input name="id" class="tbl-inputs" value="<%=booking.getBookingID()%>" size="2" readonly >
                            </td>
                            <td><%=booking.getPickupAddress()%></td>
                            <td><%=booking.getDateAndTime()%></td>
                            <td><%=booking.getDestinationAddress()%></td>
                            <td><%=booking.getBookingCharge()%></td>
                            <td><%=booking.getCustomerName()%></td>
                            <td class="options">
                                <select class="form-control form-control-sm select-box" name="drivername">
                                    <option>Re-assign</option>
                                    <% for (Driver driver : allDrivers) {%>

                                    <% if (!driver.getUsername().equals(driverName)) {%>
                                    <option><%=driver.getUsername()%></option>
                                    <% } %>
                                    <%}%> 
                                </select>
                            </td>
                            <td class="reject-comment"><%=booking.getRejectStatus()%></td>
                        </tr> 
                        <% }%>
                    </table>
                    <button type="submit" class="btn button-admin">Re-Assign</button>
                </form>
            </div>
            <% }%>
        </div>

        <!-- Add Driver Modal -->
        <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header register-header">
                        <h5 class="model-heading">Registration</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body register-div">
                        <form action="DriverManagerServlet" method="POST">
                            <label class="radio-inline form-group">
                                <input type="radio" name="driver" value="add" checked id="drivercheck">

                            </label>

                            <div id="driverRegistration">
                                <div class="form-group">
                                    <label for="username">Username</label>
                                    <input type="text" required="true" class="form-control input-style" name="username" id="username" placeholder="your name">
                                </div>
                                <div class="form-group">
                                    <label for="Vnum">Vehicle No</label>
                                    <input type="text" required="true" class="form-control input-style" name="vehicleNo" id="Vnum" placeholder="P0000">
                                </div>
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" required="true" class="form-control input-style" name="password" id="password" placeholder="password">
                                </div>
                                <div class="form-group">
                                    <label for="rPassword">Retype Password</label>
                                    <input type="password" required="true" class="form-control input-style" name="password2" id="rPassword" placeholder="re-type password">
                                </div>
                            </div>


                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-warning">Add Driver</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="removeDriverModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header register-header">
                        <h5 class="modal-title model-heading" id="exampleModalLongTitle">Driver Remove</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>

                    </div>
                    <form action="DriverManagerServlet" method="POST">
                        <div class="modal-body register-div">
                            <div>
                                <input type="radio" name="driver" value="remove" checked id="drivercheck">
                                <select name="selected" class="form-control input-style" style="color: white;">
                                    <option>Select</option>
                                    <% for (Driver driver : allDrivers) {%>
                                    <option><%=driver.getUsername()%></option>
                                    <% }%>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer register-div">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-warning">Remove Driver</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="reports-container" id="tableDiv">
            <h3 id="heading" class="text-white summary-h3 "> </h3>
            <table class="table table-striped summary-table table-font-size" id="dataTable"> </table>
            <div class="report-summary-container">

                <table class="table table-striped summary-table table-font-size" id="report-summary">
                    <tr>
                        <td>Turnover</td>
                        <td id="sumCell"></td>
                        <td>No of Customers</td>
                        <td id="countCell"></td>
                    </tr>
                </table>
            </div>
        </div> 

        <script>
            $(".msg").fadeOut(3000).delay(3000).fadeOut('slow');
        </script>
    </body>
</html>