var app=angular.module('profile', ['ui.grid']);

app.config(['$httpProvider', function ($httpProvider) {
	  //Reset headers to avoid OPTIONS request (aka preflight)
	  $httpProvider.defaults.headers.common = {};
	  $httpProvider.defaults.headers.post = {};
	  $httpProvider.defaults.headers.put = {};
	  $httpProvider.defaults.headers.patch = {};
	}]);

var centric01_basic="https://centric01-main.herokuapp.com";
var centric02_basic="https://centric02-social.herokuapp.com";

app.controller('user_data', function ($scope,$rootScope, $http) {
	
	$scope.user_data;
	
	$scope.edit_username;
	$scope.edit_firstname;
	$scope.edit_lastname;
	$scope.edit_sex;
	$scope.edit_birthdate;
	$scope.edit_username_visibility;
	$scope.edit_city;
	$scope.edit_country;
	
	
	$scope.loadUserData = function () {
        console.log('loadUserData called.');
        $(".loading_data").show();
        
        $http({
            url: centric01_basic+"/user/data/"+global_username,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
            $scope.user_data = success.data;
            
            var dateToFormat = $scope.user_data.birthdate;
    		var dateObj = new Date(dateToFormat);
			var month = dateObj.getUTCMonth() + 1;
			var day = dateObj.getUTCDate();
			var year = dateObj.getUTCFullYear();
			var seconds = dateObj.getSeconds();
			var minutes = dateObj.getMinutes();
			var hour = dateObj.getHours();
			var newdate = year + "/" + month + "/" + day
			
			$scope.user_data.birthdate = newdate;
            
            $(".loading_data").hide();
        }, function(error){
        	console.log('error');
        });
        
        console.log($scope.user_data);
       
    };
    
    
    $scope.editData=function(){
    	$scope.edit_username=$scope.user_data.username;
    	$scope.edit_firstname=$scope.user_data.firstname;
    	$scope.edit_lastname=$scope.user_data.lastname;
    	$scope.edit_sex=$scope.user_data.sex;
    	$scope.edit_birthdate=new Date($scope.user_data.birthdate);
    	$scope.edit_username_visibility=$scope.user_data.usernameVisible;
    	$scope.edit_city=$scope.user_data.city;
    	$scope.edit_country=$scope.user_data.country;
    	$("#editDataModal").modal('show');
    }
    
    $scope.editSave=function(){
    	$scope.user_data.username=$scope.edit_username;
    	$scope.user_data.firstname=$scope.edit_firstname;
    	$scope.user_data.lastname=$scope.edit_lastname;
    	$scope.user_data.sex=$scope.edit_sex;
    	$scope.user_data.birthdate=$scope.edit_birthdate.getTime() + 1000 * 60 * 60 * 24;
    	$scope.user_data.usernameVisible=$scope.edit_username_visibility;
    	$scope.user_data.city=$scope.edit_city;
    	$scope.user_data.country=$scope.edit_country;
    	$("#editDataModal").modal('hide');
    	$(".loading_data").show();
    	
    	//$http.put(centric01_basic+"/user/data/"+global_username, $scope.user_data)
    	
    	$http({
            url: centric01_basic+"/user/data/"+global_username,
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: $scope.user_data
        }).then(function(success)
        {
            console.log('saved');
            $scope.loadUserData();
        },function(error)
        {
            console.log('error');
        });
    }
    
    /*trigger the function during the page loading*/
    $scope.loadUserData();
    
	
});

app.controller('sentence_receviver', function ($scope,$rootScope, $http){
	$scope.myData;
	
	$scope.userToReplay;
	
	$scope.measuretypes;
	
	$scope.typeForResponse;
	
	$scope.modeToReplay="gain";
	
	
	$scope.loadData=function(){
		$http({
            url: centric02_basic+"/sentence/"+global_username,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
            $scope.myData = success.data;
            $('.loaderSentences').hide();
            $('.no_sentence').hide();
            for(i=0;i<$scope.myData.length;i++){
            	$scope.loadSentenceDetails($scope.myData[i]);
            }
        }, function(error){
        	console.log('error');
        	$('.loaderSentences').hide();
        	$('.no_sentence').show();
        });
	}
	
	$scope.loadSentenceDetails=function(sentence){
		$http({
            url: centric02_basic+"/sentence/"+global_username+"/"+sentence.idSentence,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
            sentence.url=success.data.url;
        }, function(error){
        	console.log('error');
        });
	};
	
	$scope.loadmeasuretypes=function(){
		$http({
            url: centric01_basic+"/measuretypes",
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
            $scope.measuretypes = success.data.measureType;
            console.log($scope.measuretypes);
            
        }, function(error){
        	console.log('error');
        });
	}
	
	//$interval($scope.loadData,5000)
	setInterval($scope.loadData,1000*60);
	$scope.loadData();
	$scope.loadmeasuretypes();
	
	$scope.replaySentence=function(toUser){
		console.log(toUser);
		$scope.userToReplay=toUser;
		$('#replayModal').modal('show');
	};
	
	$scope.sendReply=function(){
		console.log($scope.userToReplay+" "+$scope.typeForResponse+" "+$scope.modeToReplay);
		$('#replayModal').modal('hide');
		$http({
			url: centric02_basic+"/sentence/" + global_username + "/" + $scope.userToReplay + "/" + $scope.typeForResponse + "/" + $scope.modeToReplay,
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: null
        }).then(function(success) {
        	$('#confirmModal').modal('show');
            
        }, function(error){
        	console.log('error');
        	$('#errorModal').modal('show');
        });
	};
	
});

app.controller('foods_for_me', function ($scope,$rootScope, $http) {
	
	$scope.foodData;
	
	$scope.loadData=function(){
		$('.loaderFoods').show();
		$('.foods_card').hide();
		
		$http({
            url: centric01_basic+"/foods/"+global_username,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
            $scope.foodData = success.data.food;
            $('.loaderFoods').hide();
    		$('.foods_card').show();
    		$('.no_food').hide();
            console.log($scope.foodData);
        }, function(error){
        	console.log('error');
        	$('.no_food').show();
        	$('.loaderFoods').hide();
        });
	};
	
	$scope.loadData();
	
});




app.controller('suggestedRecipesController', function ($scope,$rootScope, $http) {
	
	// Function to load the section 'Suggested Recipes'
	$rootScope.loadSuggestedRecipes = function () {
		
		// Shows the loader
        $(".showOnLoadSuggestedRecipes").hide();
        $(".loaderSuggestedRecipes").show();
        
    	// Gets all the recipes from the server
    	$http({
            url: centric02_basic+"/recipe/suggested/" + global_username,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.suggestedRecipes = success.data.recipes;
        	console.log($scope.suggestedRecipes);
        	
        	$('.no_recipe').hide();
        	
        }, function(error){
        	console.log('Error suggested recipes');
        	$('.no_recipe').show();
        });
    	
		
	    // Hides the loader and shows the content
	    $(".loaderSuggestedRecipes").hide();
	    $(".showOnLoadSuggestedRecipes").show();
       
    };
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadSuggestedRecipes();
	
});




app.controller('weatherController', function ($scope,$rootScope, $http) {
	
	// Function to load the section 'Weather'
	$rootScope.loadWeather = function () {
		
		// Shows the loader
        $(".showOnLoadWeather").hide();
        $(".loaderWeather").show();
        console.log("Loader weather show");
    	// Gets the weather from the server
    	$http({
            url: centric01_basic + "/sentence/weather/" + global_username,
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.weatherData = success.data;
        	var wCode = $scope.weatherData.weatherCode;
        	
        	if(wCode>=800 && wCode<801)
        		$scope.weatherData.url = "images/icons/01-sunny.svg";
        	else if(wCode>=801 && wCode<803)
        		$scope.weatherData.url = "images/icons/02-sunny.svg";
        	else if(wCode>=803 && wCode<=804)
        		$scope.weatherData.url = "images/icons/06-cloud-cloud.svg";
        	else if(wCode>=701 && wCode<=781)
        		$scope.weatherData.url = "images/icons/08-mist.svg";
        	else if(wCode>=200 && wCode<=232)
        		$scope.weatherData.url = "images/icons/11-rain-thunderstorm.svg";
        	else if(wCode>=300 && wCode<=321)
        		$scope.weatherData.url = "images/icons/09-rain-light.svg"
        	else if(wCode>=500 && wCode<=531)
        		$scope.weatherData.url = "images/icons/10-rain-heavy.svg";
        	else if(wCode>=600 && wCode<=622)
        		$scope.weatherData.url = "images/icons/14-snow-heavy.svg";
        	else if(wCode>=900 && wCode<=962)
        		$scope.weatherData.url = "http://icons.iconarchive.com/icons/large-icons/large-weather/256/tornado-icon.png";
        	else
        		$scope.weatherData.url = "images/icons/03-sun-cloud.svg";
        	
        	
        	console.log($scope.weatherData);
        	
        	var today = new Date();
        	var dd = today.getDate();
        	var mm = today.getMonth()+1;
        	var yyyy = today.getFullYear();

        	if(dd<10) {
        	    dd='0'+dd
        	} 

        	if(mm<10) {
        	    mm='0'+mm
        	} 
        	
        	var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        	var dayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        	
    		var literalMonth = monthNames[today.getMonth()];
    		var literalDay = dayNames[today.getDay()];
    		
        	today = dd+' '+literalMonth;
        	
        	$scope.weatherData.date = today;
        	$scope.weatherData.day = literalDay;
        	
        	$(".no_weather").hide();
        	
        }, function(error){
        	console.log('Error weather');
        	$(".no_weather").show();
        });
    	
		
	    // Hides the loader and shows the content
	    $(".loaderWeather").hide();
	    $(".showOnLoadWeather").show();
	    
       
    };
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadWeather();
	
});
	

