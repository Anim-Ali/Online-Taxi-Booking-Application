<%-- 
    Document   : customer
    Created on : Sep 29, 2020, 4:35:31 PM
    Author     : Dhashin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer View</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script> 
        <script src="js/customer.js"></script>
        <script type="text/javascript">
        var username = '<%= session.getAttribute("user") %>';
        username = username.replace(/&#39;/g, "'");
        </script>
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
                                <button type="button" class="btn button-driver" id="new-booking">New Booking</button>
                            </li>
                            <li class="nav-item">
                                <button type="button" class="btn button-driver" id="booking-list">My Bookings</button>
                            </li>
                            <li class="nav-item">
                                <a class="btn button-driver" href="LogoutServlet">Sign Out</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </section>
        
        <% String msg = (String) request.getAttribute("msg");
            if(msg != null){ %>
            <div class="container alert alert-success msg mt-5" role="art">
                <%=msg %>
                
            </div>
           <% } %>
        
        
        <div class="container text-white mt-5 map-div" id ="map-div">

            <h1 id="map-h1">Click on the map to select current location and destination</h1>
            
            <div class="floating-panel">
                <button type="button" class="btn btn-outline-light map-btn" id="disBtn">Get Distance</button>
                <button type="button" class="btn btn-outline-light map-btn" onclick="reset()">Reset</button>
            </div>

            <div id="map" class="content"></div>


            <div class="booking-details">
                <p></p>
                <h3>Booking details</h3>
                <form id="myForm" action="CustomerServlet" method="POST">
                    <p>
                    <label for="pickuptime" >Select date and time:</label>
                    <input class="form-control form-control-sm input-style" type="datetime-local" id="pickuptime" name="datetime" required>
                    </p>
                    <p>
                        <label for="pickup">Pick up:</label>
                        <input class="form-control form-control-sm input-style" id="pick" type="text" name="pickup" required readonly>
                    </p>
                    <p>
                        <label for="dropoff">Drop off:</label>
                        <input class="form-control form-control-sm input-style" id="drop" type="text" name="dropoff" required readonly>
                    </p>
                    <p>
                        <label for="tdist">Total Distance:</label>
                        <input class="form-control form-control-sm input-style" id="tdist" type="text" name="tdist" required readonly>
                    </p>
                    <button type="submit" class="btn button-admin" name="formSubmit">Make booking</button>
                </form>
            </div>
        </div>
        <div class="reports-container" id="tableDiv">
            <h3 id="heading" class="text-white summary-h3 "> </h3>
            <table class="table table-striped summary-table table-font-size" id="dataTable"> </table>
        </div>

        <script
            src="https://maps.googleapis.com/maps/api/js?key="YOUR_KEY"&callback=initMap&libraries=&v=weekly"
            defer
        ></script>
        <script src="js/gmap.js" ></script>
        <script>
            $(".msg").fadeOut(3000).delay(3000).fadeOut('slow');
        </script>
    </body>
</html>
