var app = angular.module('Coupon', ['ngCookies','base64']);

app.controller('Ctrl', function($scope, $http, $cookies, $window, $base64) {

	$scope.ines = {};
	$scope.jonathan = {};
			 			 			 
	$scope.readInes = function() {
		console.log("Read Balances Ines");
		$http.get('/coupon/balance/ines' )
			 .then(  function(response) {
				   	console.log("Balance Ines read");
				   	$scope.ines = response.data;
				   	console.log("Response: " + $scope.ines );
			 });
    };

    $scope.readJonathan = function() {
		console.log("Read Balances Joni");
		$http.get('/coupon/balance/jonathan' )
		    .then(  function(response) {
				 	console.log("Balances Jonathan read");
			   		$scope.jonathan = response.data;
			   		console.log("Response: " + $scope.jonathan);
		 });
	};

	$scope.authorize = function() {
		var token = "";

		// Get Password from URL
		var url = $window.location.href;
		console.log("URL: " + url);
		var qparams = url.split('?');
		if (qparams.length > 1) {
			var params = new URLSearchParams(qparams[1]);
			var value = params.get("password");	
			console.log("QParam[Password] = " + value);
			token = "Basic " + $base64.encode("ADMIN" + ':' + value);
		}

		// Get Password from current Authorization Header
		if (token === "") {
			var value = $http.defaults.headers.common["Authorization"];
			console.log("Authorization Header: " + value);
			if (value != "" && typeof value != 'undefined') {
				token = value;
			}
		} 

		// Get Password from Cookies
		if (token === "") {
			var value = $cookies.get('coupon_auth');
			console.log("Stored Cookie: " + value);
			if (value != "" && typeof value != 'undefined') {
				token = value;
			}
		}

		// Save Password and Set Authorization
		if (token != "") {
			var exp_date = new Date(3000,12,31);
			$cookies.put('coupon_auth', token, { expires: exp_date });
			console.log("Setting Cookie: " + token);
			console.log("Cookie: " + $cookies.get('coupon_auth'));

			$http.defaults.headers.common['Authorization'] = token;
			return true;
		} else {
			console.log("Cookie: " + $cookies.get('coupon_auth'));
			return false;
		}
	};

	$scope.read = function() {
		var authorized = $scope.authorize();
		if ( authorized === true ) {
			console.log("Password found --> Read")
			$scope.readInes();
			$scope.readJonathan();
		} else {
			console.log("No Password --> Login")
			$window.location.href = "/login";
		}
	}
	$scope.read();

	$scope.issue = function(name) {
		console.log("Issue Coupon for " + name);
		url = 'coupon/issue/' + name + '/25';
		console.log("URL: " + url);
		$http.post(url)
		     .then( function success(response) {
		    			console.log("Coupon issued");
						$scope.readInes();
						$scope.readJonathan();
		    		}, function error(response) {
		    	      	console.log("Error while issuing coupon");
		    	      	response.data.errors.forEach(function(e) {
		    	    	  	console.log(e);
		    	      	})
		    	   });
	};

});		
