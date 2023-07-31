/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() { 
    $("button").click(function(event) { // triggers when any button is clicked
        var buttonID = event.target.id; // get buttonID
        // call relevent function
        if (buttonID === "booking-list") {
            reports(buttonID, username);
        } else if (buttonID === "new-booking") {    
            document.forms[0].action = "customer.jsp";
            document.forms[0].method = "post"; // "get"
            document.forms[0].submit();
            clear("#map-div");
        }else if (buttonID === "clear") {
            clear();
        }
    }); 
}); 

// displays reports based on the button pressed
function reports(buttonID, username) {
    $.get('customerReports', {"button-id": buttonID, "username": username}, function(responseJson) { //call servlet and pass button-ID
        if(responseJson !== null){ 
            $("#dataTable tr").remove(); // remove table contents
            var table1 = $("#dataTable"); 
            clear("#tableDiv"); //clear other divs from webpage
            if (buttonID === "booking-list") {
                bookings(table1, responseJson);
                heading.innerText = "My Bookings";
            }
        }
    }); 
}

// fill in bookings table
function bookings(table1, responseJson) {
    var firstRow = $("<tr> <th>ID</th><th>Pick Up</th><th>Date & Time</th><th>Drop Off</th>"
                    + "<th>Charge</th><th>Driver</th><th>Vehicle No</th><th>Status</th></tr>");
    firstRow.appendTo(table1);
    
    var keys = [
        "BookingID",
        "PickupAddress",
        "DateAndTime",
        "DestinationAddress",
        "BookingCharge",
        "DriverName",
        "VehicleNo",
        "BookingStatus",
      ];
    $.each(responseJson, function(i, obj) {
        var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
        keys.forEach((key, i) => {
            if (key === "BookingStatus") {
                if (obj[key] === false) {
                    rowNew.children().eq(i).text("Incomplete");
                } else {
                    rowNew.children().eq(i).text("Complete");
                }
            } else {
                rowNew.children().eq(i).text(obj[key]);
            }
        });
        rowNew.appendTo(table1);
    });                     
}

// hide divs from webpage excluding the parameter div
function clear(showDiv) {
    var divs = ["#map-div", "#tableDiv", "#booking-confirmation-div"];
    for (var i = 0; i < divs.length; i++) {
        if (divs[i] != showDiv) {
            $(divs[i]).hide();
        } else {
            $(divs[i]).show();
        }
    }
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
