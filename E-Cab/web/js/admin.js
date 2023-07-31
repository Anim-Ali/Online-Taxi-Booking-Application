/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() { 
    //pageLoad();
    clear("#assignDiv");
    $("button").click(function(event) { // triggers when any button is clicked
        var buttonID = event.target.id; // get buttonID
        // call relevent function
        if (buttonID === "bookings" || buttonID === "drivers" || buttonID === "customers" ||
                buttonID === "dailyDrivers" || buttonID === "dailyBookings") {
            reports(buttonID);
            
        } else if (buttonID === "clear") {
            clear();
        } else if (buttonID === "assignPage") {
            clear("#assignDiv");
        }
    }); 
}); 

// displays reports based on the button pressed
function reports(buttonID) {
    $.get('adminReports', {"button-id": buttonID}, function(responseJson) { //call servlet and pass button-ID
        if(responseJson !== null){ 
            $("#dataTable tr").remove(); // remove table contents
            var table1 = $("#dataTable"); 
            // alert (table1);
            clear("#tableDiv"); //clear other divs from webpage
            if (buttonID === "bookings" || buttonID === "dailyBookings" || buttonID === "dailyDrivers") {
                bookings(table1, responseJson);
                heading.innerText = "Bookings";
                turnover();
                countCustomers();
            }
            else if (buttonID === "drivers") {
                drivers(table1, responseJson);
                heading.innerText = "Drivers";
            }
            else if (buttonID === "customers") {
                customers(table1, responseJson);
                heading.innerText = "Customers";
            }
            var options = {
                debug: true,
                emptyText: "    ",
            }
            $('#dataTable').ddTableFilter(options);
        }
    }); 
}

// fill in bookings table
function bookings(table1, responseJson) {
    var firstRow = $("<tr> <th>ID</th><th>Pick Up</th><th>Date</th><th>Drop Off</th>"
                    + "<th>Charge</th><th>Customer</th><th>Driver</th><th>Vehicle No</th>"
                    + "<th>Status</th><th>Rejection Status</th></tr>");
    firstRow.appendTo(table1);
    
    var keys = [
        "BookingID",
        "PickupAddress",
        "DateAndTime",
        "DestinationAddress",
        "BookingCharge",
        "CustomerName",
        "DriverName",
        "VehicleNo",
        "BookingStatus",
        "RejectStatus"
      ];
    $.each(responseJson, function(i, obj) {
        var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
        keys.forEach((key, i) => {
            if (key === "BookingStatus") {
                if (obj[key] === false) {
                    rowNew.children().eq(i).text("Incomplete");
                } else {
                    rowNew.children().eq(i).text("Complete");
                }
            } else if (key === "DateAndTime") {
                var dt = new Date(obj[key]);
                var date = dt.getFullYear() + "/" + (dt.getMonth() + 1) + "/" + dt.getDate();
                rowNew.children().eq(i).text(date);
                //var time = ('0'  + dt.getHours()).slice(-2)+':'+('0' + dt.getMinutes()).slice(-2);
                //rowNew.children().eq(i).text(time);
            } else {
                rowNew.children().eq(i).text(obj[key]);
            }
        });
        rowNew.appendTo(table1);
    });  
    document.getElementById("report-summary").style.display = "table";
}

// fill in customers table
function customers(table1, responseJson) {
    var firstRow = $("<tr> <th>Name</th> <th>No of Bookings</th> <th>Total Customer Charges</th> </tr>");
    firstRow.appendTo(table1);
    var keys = [
        "name",
        "totalBookingsNo",
        "totalCharges"
      ];
    $.each(responseJson, function(i, obj) {
        var rowNew = $("<tr><td></td><td></td><td></td></tr>");
        keys.forEach((key, i) => {
          rowNew.children().eq(i).text(obj[key]);
        });
        rowNew.appendTo(table1);
    });    
    document.getElementById("report-summary").style.display = "none";
}

// fill in drivers table
function drivers(table1, responseJson) {
    var firstRow = $("<tr> <th>Name</th> <th>Vehicle No</th> <th>No of Bookings Assigned</th>"
                    + "<th>No of Bookings Completed</th> <th>Total Turnover</th></tr>");
    firstRow.appendTo(table1);
    var keys = [
        "name",
        "vehicleNo",
        "totalBookingsNo",
        "completeBookingsNo",
        "totalCharges"
      ];
    $.each(responseJson, function(i, obj) {
        var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
        keys.forEach((key, i) => {
          rowNew.children().eq(i).text(obj[key]);
        });
        rowNew.appendTo(table1);
    });      
    document.getElementById("report-summary").style.display = "none";
}

// hide divs from webpage excluding the parameter div
function clear(showDiv) {
    var divs = ["#assignDiv", "#tableDiv"];
    for (var i = 0; i < divs.length; i++) {
        if (divs[i] != showDiv) {
            $(divs[i]).hide();
        } else {
            $(divs[i]).show();
        }
    }
}

function turnover() {
    var table = document.getElementById("dataTable"), sum = 0;
    for (var i = 1; i < table.rows.length; i++) {
        //if (!(table.rows[i].style.display === "none")) {
        if ((!(table.rows[i].style.display === "none")) && (table.rows[i].cells[8].innerHTML === "Complete")) {
            //console.log(table.rows[i].cells[4].innerHTML);
            sum = sum + parseInt(table.rows[i].cells[4].innerHTML);
        }
    }
    console.log("sum: " + sum);
    document.getElementById("sumCell").innerHTML = sum;
}

function countCustomers() {
    var table = document.getElementById("dataTable"), count = 0;
    for (var i = 1; i < table.rows.length; i++) {
        if ((!(table.rows[i].style.display === "none")) && (table.rows[i].cells[8].innerHTML === "Complete")) {
            count++;
        }
    }
    console.log("count: " + count);
    document.getElementById("countCell").innerHTML = count;
}

var loaded = false;
// load webpage with only navbar 
// --> function does not work as expected due to page reloading
function pageLoad() {
  if (loaded === false) {
    loaded = true;
    clear();
  }
}