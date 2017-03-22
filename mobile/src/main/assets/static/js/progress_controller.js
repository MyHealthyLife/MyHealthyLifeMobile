var app=angular.module('progress', []);

var centric01_basic="https://centric01-main.herokuapp.com";

app.controller('currentHealthController', function ($scope,$rootScope, $http) {
	
	$scope.user_data
	
	// Function to load the section 'Current Health'
	$rootScope.loadCurrentHealth = function () {
		
		// Shows the loader
        $(".showOnLoadCurrentHealth").hide();
        $(".loaderCurrentHealth").show();
        
        // Request to get the data of a user (including his/her health status)
        $http({
            url: centric01_basic+"/user/data/"+global_username,
            method: 'GET',
            params: {
            }
        }).then(function(success) {
            
        	// Memorizes the data in the scope
        	$scope.user_data = success.data;
            
        	// Gets the list of measures in the health profile
            var measures = $scope.user_data.healthProfile.currentHealth.measure;
            
            // For each measure it checks the type
            for (i = 0; i < measures.length; i++) { 
                
            	if(measures[i].measureType=='weight')
            		$scope.weight = measures[i].measureValue;
            	if(measures[i].measureType=='height')
            		$scope.height = measures[i].measureValue;
            	if(measures[i].measureType=='steps')
            		$scope.steps = measures[i].measureValue;
            	if(measures[i].measureType=='bloodpressure')
            		$scope.bloodpressure = measures[i].measureValue;
            }
            
            // Hides the loader and shows the content
            $(".loaderCurrentHealth").hide();
            $(".showOnLoadCurrentHealth").show();
            
        }, function(error){
        	console.log('Error current health');
        });
        
       
    };
    
    // Calls instantly the function to load the current health as soon as the controller is ready
    $rootScope.loadCurrentHealth();
    
	
});








app.controller('progressBarController', function ($scope,$rootScope, $http) {
	
	$scope.goal_data
	$scope.weightBar = 100;
	$scope.heightBar = 100;
	$scope.stepsBar = 100;
	$scope.bpBar = 100;
	
	// Function to load the section 'Goals Progress'
	$rootScope.loadProgressBars = function () {

		// Shows the loader
        $(".showOnLoadProgressBars").hide();
        $(".loaderProgressBars").show();
        
        // Request to get the goals of a user
        $http({
            url: centric01_basic+"/user/goals/"+global_username,
            method: 'GET',
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.goal_data = success.data;

        	// Gets the list of goals in the data returned from the server
            var goals = $scope.goal_data.goals;

            // For each goal it checks the type and calculates the percentage
            for (i = 0; i < goals.length; i++) { 
            	console.log(goals[i]);
            	
            	if(goals[i].actualValue > goals[i].valueToReach) {
            		goals[i].actualValue = goals[i].actualValue - (Math.abs(goals[i].difference)) * 2;
            		
            		if(goals[i].actualValue < 0)
            			goals[i].actualValue = 0;
            	}
            	
            	if(goals[i].goalName=='weight')
            		$scope.weightBar = parseInt(100 * ((Math.abs(goals[i].actualValue) / Math.abs(goals[i].valueToReach))));
            	if(goals[i].goalName=='height')
            		$scope.heightBar = parseInt(100 * ((Math.abs(goals[i].actualValue) / Math.abs(goals[i].valueToReach))));
            	if(goals[i].goalName=='steps')
            		$scope.stepsBar = parseInt(100 * ((Math.abs(goals[i].actualValue) / Math.abs(goals[i].valueToReach))));
            	if(goals[i].goalName=='bloodpressure')
            		$scope.bpBar = parseInt(100 * ((Math.abs(goals[i].actualValue) / Math.abs(goals[i].valueToReach))));
            	
            }

            // Hides the loader and shows the content
            $(".loaderProgressBars").hide();
            $(".showOnLoadProgressBars").show();
            
        }, function(error){
        	console.log('Error progress bars');
        });
        
    };

    // Calls instantly the function to load the progress bars as soon as the controller is ready
    $rootScope.loadProgressBars();
    
    
});





app.controller('addMeasureController', function ($scope,$rootScope, $http) {
	
	$scope.measureTypes
	$scope.measureData = {}

	// Function to load the section 'Add Measure'
	$rootScope.loadAddMeasure = function () {
		
		// Shows the loader
        $(".showOnLoadAddMeasure").hide();
        $(".loaderAddMeasure").show();

        // Request to get the measure types available in the system
        $http({
            url: centric01_basic+"/measuretypes",
            method: 'GET',
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.measureTypes = success.data.measureType;

            // Hides the loader and shows the content
            $(".loaderAddMeasure").hide();
            $(".showOnLoadAddMeasure").show();
            
            
        }, function(error){
        	console.log('error');
        });
       
    };
    
    // Function to send a request for adding a new measure
    $scope.addMeasureSave=function(){

		// Shows the loader
    	$(".showOnLoadAddMeasure").hide();
        $(".loaderAddMeasure").show();
        
        // Gets the data the user inserted in the form
    	$scope.measureData.measureType=$scope.add_measureType;
    	$scope.measureData.measureValue=$scope.add_measureValue;
    	$scope.measureData.dateRegistered=$scope.add_measureDate;

        // Request to post the measure the user wants to insert
    	$http({
            url: centric01_basic+"/measure/"+global_username,
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            data: $scope.measureData
        }).then(function(success)
        {
        	// Reloads all the components in the page
        	$rootScope.loadProgressBars();
            $rootScope.loadMeasureHistory();
            $rootScope.loadCurrentHealth();

            // Hides the loader and shows the content
            $(".loaderAddMeasure").hide();
            $(".showOnLoadAddMeasure").show();
            
        },function(error)
        {
            console.log('Error save measure');
        });
    }
    

    // Calls instantly the function to load the progress bars as soon as the controller is ready
    $rootScope.loadAddMeasure();
	
});




app.controller('measureHistoryController', function ($scope,$rootScope, $http) {
	
	$scope.measureHistory

	// Function to load the section 'Measure History'
	$rootScope.loadMeasureHistory = function () {
		
		// Shows the loader
        $(".showOnLoadHistory").hide();
        $(".loaderHistory").show();
        
        // Request to get the measure history of the user
        $http({
            url: centric01_basic+"/measure/"+global_username+"/history",
            method: 'GET',
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.measureHistory = success.data.measures;
        	var j = $scope.measureHistory.length-1;
        	
        	// Cycle to get a readable date object in a string representation format
        	for(i=0;i<$scope.measureHistory.length;i++) {
        		
        		var dateToFormat = $scope.measureHistory[i].dateRegistered;
        		var dateObj = new Date(dateToFormat);
				var month = dateObj.getUTCMonth() + 1;
				var day = dateObj.getUTCDate() + 1;
				var year = dateObj.getUTCFullYear();
				var seconds = dateObj.getSeconds();
				var minutes = dateObj.getMinutes();
				var hour = dateObj.getHours();
				var newdate = year + "/" + month + "/" + day + " " + hour + ":" + minutes + ":" + seconds
        		
				// Saves the new format in the scope
				$scope.measureHistory[i].dateRegistered = newdate;
        	}
        	
        	$scope.measureHistory = $scope.measureHistory.reverse();

            // Hides the loader and shows the content
            $(".loaderHistory").hide();
            $(".showOnLoadHistory").show();
            
            
        }, function(error){
        	console.log('Error load measure history');
        });
        
       
    };
    

    // Function to send a request to delete an existing measure from the history   
    $scope.deleteMeasure=function(measureId){

		// Shows the loader
    	$(".showOnLoadHistory").hide();
        $(".loaderHistory").show();

        // Request to delete a measure in the history
    	$http({
            url: centric01_basic+"/measure/"+global_username+"/"+measureId,
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json"
            },
        }).then(function(success)
        {

        	// Reloads all the components in the page
        	$rootScope.loadProgressBars();
            $rootScope.loadMeasureHistory();
            $rootScope.loadCurrentHealth();

            // Hides the loader and shows the content
            $(".loaderHistory").hide();
            $(".showOnLoadHistory").show();
            
        },function(error)
        {
            console.log('Error delete measure from history');
        });
    }

    // Calls instantly the function to load the progress bars as soon as the controller is ready
    $rootScope.loadMeasureHistory();
	
});




