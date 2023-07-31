<%-- 
    Document   : loginjsp
    Created on : Sep 28, 2020, 5:05:23 PM
    Author     : Dhashin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Login Form</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="css/styles.css">
        <link
            href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
        
    </head>

    <body>
        <% String err = (String) request.getAttribute("Error");
            if (err != null) { %>
        <div class="alert alert-danger" role="alert">
            ${requestScope["Error"]}
        </div>
        <% }%>

        <div class="login-main-div">
            <form action="login" method="POST">
            <div class="position-div">
                <div class="login-box">
                    <div class="">
                        <img src="taxi.png" alt="">
                    </div>
                    <div>
                        <h3>E-Cab</h3>
                    </div>
                    <div>
                        <div class="input_box">
                            <i class="fas fa-user"></i>
                            <input type="text" placeholder="Username" name="username">
                        </div>
                        <div class="input_box">
                            <i class="fas fa-lock"></i>
                            <input type="password" placeholder="Password" name="password">
                        </div>
                    </div>
                    <div>
                        <button class="login-btn" type="submit" name="Login">Login</button>
                    </div>
                    <div>
                        <button class="register-btn" type="button" name="Login" data-toggle="modal" data-target="#registerModal">Register</button>
                    </div>
                </div>
            </div>
            </form>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header register-header">

                        <h5>Registration</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body register-div">
                        <form action="RegisterServlet" method="POST">
                            <label class="radio-inline form-group">
                                <input type="radio" name="usertype" value="customer" checked id="drivercheck">
                            </label>
                           
                            <div class="form-group">
                                <label for="username" style="margin-top: 30px;">Username</label>
                                <input type="text" required="true" class="form-control form-control-sm" name="username" id="username" placeholder="Username">
                            </div>
                            
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" required="true" class="form-control form-control-sm" name="password" id="password" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="rPassword">Re-type Password</label>
                                <input type="password" required="truea" style="margin-bottom: 50px;" class="form-control form-control-sm" name="password2" id="rPassword" placeholder="Re-type your password">
                            </div>
                            
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-warning" >Register</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </body>
    <script src="https://kit.fontawesome.com/4f337ab72d.js" crossorigin="anonymous"></script>
</html>
