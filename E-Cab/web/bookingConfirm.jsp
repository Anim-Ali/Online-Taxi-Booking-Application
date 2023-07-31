<%--
    Document   : bookingConfirm
    Created on : Oct 19, 2020, 1:18:50 AM
    Author     : salaam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Confirm</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
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
                                <button type="button" action="customer.jsp" class="btn button-driver" id="new-booking">New Booking</button>
                            <!-- </li>
                            <li class="nav-item">
                                <button type="button" class="btn button-driver" id="booking-list">My Bookings</button>
                            </li> -->
                            <li class="nav-item">
                                <a class="btn button-driver" href="LogoutServlet">Sign Out</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </section>
        
        <div class="container mt-3 text-white " id="booking-confirmation-div">
            <h1 class="bc-h1">Confirm your booking</h1>
            <div class="d-flex flex-row confirm-div">
                <div id="cmap">

                </div>
                <div class="confirm-box ml-5 d-flex flex-column justify-content-center">
                    <form action="BookingConfirmServlet" method="POST">
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="pickup">Pick up</label>
                            <input class="form-control form-control-sm input-style" id="pick" type="text" name="pickup" value="${sessionScope["pick"]}" readonly><br>
                        </div>

                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="dropoff">Drop off</label>
                            <input class="form-control form-control-sm input-style" id="drop" type="text" name="dropoff" value="${sessionScope["drop"]}" readonly><br>
                        </div>
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="dist">Distance</label>
                            <input class="form-control form-control-sm input-style" id="dist" type="text" name="tdist" value="${sessionScope["distance"]}" readonly><br>
                        </div>
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="datetime">Date & Time</label>
                            <input class="form-control form-control-sm input-style" id="datetime" type="text" name="datetime" value="${sessionScope["datetime"]}" readonly><br>
                        </div>
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="price">Price</label>
                            <input class="form-control form-control-sm input-style" id="price" type="text" name="price" value="${sessionScope["price"]}" readonly><br>
                        </div>
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="gst">GST</label>
                            <input class="form-control form-control-sm input-style" id="gst" type="text" name="price" value="${sessionScope["gst"]}" readonly><br>
                        </div>
                        <div class="form-group form-inline bc-div">
                            <label class="c-label" for="tprice">Total Price</label>
                            <input class="form-control form-control-sm input-style" id="tprice" type="text" name="price" value="${sessionScope["tprice"]}" readonly><br>
                        </div>
                        <div class="mt-5 d-flex flex-row justify-content-around">
                            <input class="btn button-admin" type="submit" value="Confirm">
                            <a class="btn button-admin" href="CustomerServlet">Cancel</a>
                        </div>

                    </form>
                </div>

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
    <script type="text/javascript" src="js/confirm.js"></script>
</body>
</html>
