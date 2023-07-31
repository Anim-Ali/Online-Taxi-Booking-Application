<%-- 
    Document   : driver
    Created on : Sep 29, 2020, 4:35:42 PM
    Author     : Dhashin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Booking"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Driver View</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script> 
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
                                <a class="btn button-driver" href="LogoutServlet">Sign Out</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </section>

        <%
            ArrayList<Booking> assignedJobs = (ArrayList<Booking>) session.getAttribute("assignedJobs");
            ArrayList<Booking> todaysJobs = (ArrayList<Booking>) session.getAttribute("todaysjobs");
        %>

        <div class="container text-white">

            <% if (todaysJobs.size() > 0) { %>
            <div class="driver-main-div">
                <form action="DriverServlet" method="POST">
                    <h3 class="jobs-h3">Today's Jobs</h3>
                    <table class="table summary-table table-striped">
                        <thead class="table-head">
                        <th>ID</th>
                        <th>Pick up</th>
                        <th>Drop Off</th>
                        <th class="th-width">Booking Charge</th>
                        <th>Date & Time</th>
                        <th>Status</th>
                        <th>Comment</th>
                        </thead>
                        <% for (Booking booking : todaysJobs) {%>
                        <tr>                           
                            <td>
                                <input name="id" class="tbl-inputs" value="<%=booking.getBookingID()%>" size="2" readonly >
                            </td>
                            <td><%=booking.getPickupAddress()%></td>
                            <td><%=booking.getDestinationAddress()%></td>
                            <td><%=booking.getBookingCharge()%></td>
                            <td><%=booking.getDateAndTime()%></td>
                            <td>
                                <select name="status" class="form-control form-control-sm select-box" onchange="allowComment(this.parentElement)" id="status">
                                    <option >Select</option>
                                    <option>Completed</option>
                                    <option>Reject</option>
                                </select>
                            </td>
                            <td>
                                <input name="comment" class="tbl-inputs" type="text" value="None" readonly>
                            </td>                           
                        </tr>
                        <% }  %>
                    </table>
                    <button class="btn button-admin">Update Today's Jobs</button>
                </form>
            </div>
            <% } else { %>
            <div class="alert alert-success msg mt-5" role="alert">
                You have no jobs today!
            </div>

            <% } %>

            <% if (assignedJobs.size() > 0) { %>
            <div class="driver-main-div">
                <form action="DriverServlet" method="POST">   
                    <h3 class="jobs-h3">All assigned Jobs</h3>
                    <table class="table summary-table table-striped">
                        <thead class="table-head">
                        <th>ID</th>
                        <th>Pick up</th>
                        <th>Drop Off</th>
                        <th class="th-width">Booking Charge</th>
                        <th>Date & Time</th>
                        <th>Status</th>
                        <th>Comment</th>
                        </thead>
                        <% for (Booking booking : assignedJobs) {%>
                        <tr>                           
                            <td>
                                <input name="id" class="tbl-inputs" value="<%=booking.getBookingID()%>" size="2" readonly >
                            </td>
                            <td><%=booking.getPickupAddress()%></td>
                            <td><%=booking.getDestinationAddress()%></td>
                            <td><%=booking.getBookingCharge()%></td>
                            <td><%=booking.getDateAndTime()%></td>
                            <td>
                                <select name="status" class="form-control form-control-sm select-box" onchange="allowComment(this.parentElement)" id="status">
                                    <option >Select</option>
                                    <option>Reject</option>
                                </select>
                            </td>
                            <td>
                                <input name="comment" class="tbl-inputs" type="text" value="None" readonly>
                            </td>                           
                        </tr>
                        <% } %>
                    </table>
                    <button class="btn button-admin">Update Jobs</button>
                </form>
            </div>

            <% }%>

        </div>
        <script src="js/driver.js"></script>
        <script>
            $(".msg").fadeOut(3000).delay(3000).fadeOut('slow');
        </script>
    </body>
</html>
