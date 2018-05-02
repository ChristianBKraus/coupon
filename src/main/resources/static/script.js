var app = angular.module('Demo', []);

app.controller('Ctrl', function($scope, $http) {

	$scope.personen = [];
	$scope.new_person = {};
	$scope.test = { name: "Hello" };
			 			 			 
	 $scope.readPersonen = function() {
		 console.log("Lesen von Personen");
	     $http.get('/api/person' )
			  .then(  function(response) {
			  		console.log("Personen wurden gelesen");
			    	$scope.personen = response.data;
					console.log("Antwort: ");
					$scope.personen.forEach(function(el){
						console.log(el);
					});
					console.log( $scope.personen );
			  });
	};
	$scope.readPersonen();

    $scope.add = function() {
		console.log("Hinzufuegen von Person");
		$http.post('api/person')
		     .then( function success(response) {
		    			console.log("Person hinzugefuegt");
		    			$scope.readPersonen();
		    		}, function error(response) {
		    	      	console.log("Fehler beim hinzufuegen von Personen");
		    	      	response.data.errors.forEach(function(e) {
		    	    	  	console.log(e);
		    	      	})
		    	   });
	};
});		
