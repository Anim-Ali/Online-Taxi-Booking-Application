

console.log("gmap.js file loaded");

let map;
let directionsService;
let directionsRenderer;
let geocoder;
let origin;
let destination;
let disBtn = document.getElementById('disBtn');

disBtn.disabled = true;

function initMap() {
    const center = {lat: 4.1755, lng: 73.5093};
    const options = {
        zoom: 16,
        center: center,
        fullscreenControl: false,
        zoomControl: false,
        streetViewControl: false

    };

    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer();
    geocoder = new google.maps.Geocoder();
    map = new google.maps.Map(
            document.getElementById('map'), options);

    origin = new google.maps.Marker({
        map: map,
        draggable: true,
        label: 'P'
    })


    destination = new google.maps.Marker({
        map: map,
        draggable: true,
        label: 'D'
    })


    google.maps.event.addListener(map, 'click', function (e) {

        if (origin.getPosition() === undefined) {
            origin.setPosition(e.latLng);
            geocoder.geocode({location: e.latLng}, function (result) {
                $("#pick").val(result[1].formatted_address);
            })
        } else if (destination.getPosition() === undefined) {
            destination.setPosition(e.latLng);
            disBtn.disabled = false;
            geocoder.geocode({location: e.latLng}, function (result) {
                $("#drop").val(result[1].formatted_address);
            })
        }


    })

}
disBtn.addEventListener("click", function () {
    calDistance();
})


// function to clear all Markers
function clearMarkers() {
    origin.setPosition(undefined);
    destination.setPosition(undefined);
}

function reset() {
    directionsRenderer.setMap(null);
    clearMarkers();
    disBtn.disabled = true;
    document.getElementById("pick").value = "";
    document.getElementById("drop").value = "";
    document.getElementById("tdist").value = "";
}

// Calculate distance
function calDistance() {

    directionsRenderer.setMap(map); // Existing map object displays directions
    directionsRenderer.setOptions({
        preserveViewport: true
    });
    // Create route from existing points used for markers
    const route = {
        origin: origin.getPosition(),
        destination: destination.getPosition(),
        travelMode: 'DRIVING'
    }

    clearMarkers();
    directionsService.route(route,
            function (response, status) { // anonymous function to capture directions
                if (status !== 'OK') {
                    window.alert('Directions request failed due to ' + status);
                    return;
                } else {
                    directionsRenderer.setDirections(response); // Add route to the map
                    var directionsData = response.routes[0].legs[0]; // Get data about the mapped route
                    if (!directionsData) {
                        window.alert('Directions request failed');
                        return;
                    } else {

                        document.getElementById("pick").value = route.origin;
                        document.getElementById("drop").value = route.destination;
                        document.getElementById("tdist").value = directionsData.distance.text
                    }
                }
            });
}
