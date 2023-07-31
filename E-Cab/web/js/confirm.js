/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function initMap(){
	const center = {lat: 4.1755, lng: 73.5093};
	const options = {
		zoom: 16,
		center: center,
                fullscreenControl: false,
                zoomControl: false,
                streetViewControl: false,
                mapTypeControl: false

	};

	const map = new google.maps.Map(
		document.getElementById('cmap'), options);

	let pickup = document.querySelector('#pick').value;
	let dropoff = document.querySelector('#drop').value;
	const latoriP = pickup.slice(pickup.indexOf('(') + 1, pickup.indexOf(','))
	const lngoriP = pickup.slice(pickup.indexOf(',')+1, pickup.indexOf(')'))
	const latoriD = dropoff.slice(dropoff.indexOf('(') + 1, dropoff.indexOf(','))
	const lngoriD = dropoff.slice(dropoff.indexOf(',')+1, dropoff.indexOf(')'))

	const ori = new google.maps.LatLng(Number(latoriP), Number(lngoriP));
	const des = new google.maps.LatLng(Number(latoriD), Number(lngoriD));

	const route = {
		origin: ori,
		destination: des,
		travelMode: 'DRIVING'
	}
    // console.log('value of pick up is ' + pickup);
    // console.log('value of dropoff is ' + dropoff);

    const directionService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    const geocoder = new google.maps.Geocoder();

    directionsRenderer.setMap(map);
    directionService.route(route, function(response, status){
    	if(status !== 'OK'){
    		console.log('Failed direction routing ' + status);
    		return;
    	} else {
    		directionsRenderer.setDirections(response);
                var directionsData = response.routes[0].legs[0];
    		if (!directionsData) {
    			console.log('Directions data failed!');
    			return;
    		} else {
    			console.log(" Driving distance is " + directionsData.distance.text + " (" + directionsData.duration.text + ").");
                        
                        geocoder.geocode({location:ori}, function(result){
                            $("#pick").val(result[1].formatted_address)
                            
                        })
                        geocoder.geocode({location:des}, function(result){
                            $("#drop").val(result[1].formatted_address);
                            
                        })
    		}
    	}
    })
}