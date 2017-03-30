var app=angular.module('social', []);

var centric01_basic="https://centric01-main.herokuapp.com";
var centric02_basic="https://centric02-social.herokuapp.com";

app.controller('rankingController', function ($scope,$rootScope, $http) {
	
	$scope.ranking
	$scope.rankingDetailed = {};
	
	$rootScope.loadRanking = function () {
		
		// Shows the loader
        $(".showOnLoadRanking").hide();
        $(".loaderRanking").show();
        
        $http({
            url: centric02_basic+"/ranking/"+global_username+"?bot=false",
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
                
            },
            params: {
            }
        }).then(function(success) {
            
        	$scope.ranking = success.data;
            console.log($scope.ranking);
            
            for(i=0;i<$scope.ranking.length;i++) {
            	
            	$scope.rankingDetailed[i] = {};
            	$scope.rankingDetailed[i].username = $scope.ranking[i].username;
            	$scope.rankingDetailed[i].idPerson = $scope.ranking[i].idPerson;
            	$scope.rankingDetailed[i].firstname = $scope.ranking[i].firstname;
            	$scope.rankingDetailed[i].lastname = $scope.ranking[i].lastname;
            	$scope.rankingDetailed[i].sex = $scope.ranking[i].sex;
            	$scope.rankingDetailed[i].weight = $scope.ranking[i].weight;
            	$scope.rankingDetailed[i].steps = $scope.ranking[i].steps;
            	$scope.rankingDetailed[i].points = $scope.ranking[i].points;
            	$scope.rankingDetailed[i].telegramUsernameAvailable = $scope.ranking[i].telegramUsernameAvailable;
            	$scope.rankingDetailed[i].telegramUsernameVisible = $scope.ranking[i].telegramUsernameVisible;
            	$scope.rankingDetailed[i].telegramUsername = $scope.ranking[i].telegramUsername;
            	$scope.rankingDetailed[i].position = i + 1;
            	
                
            }
            
            // Hides the loader and shows the content
            $(".loaderRanking").hide();
            $(".showOnLoadRanking").show();
            
        }, function(error){
        	console.log('Error ranking');
        });
        
       
    };
    
    $rootScope.loadRanking();
    
	
});





app.controller('addFoodController', function ($scope,$rootScope, $http) {
	
	$scope.foodTypes;
	$scope.foodData = {};

	// Function to load the section 'Add Food'
	$rootScope.loadAddFood = function () {
		
		// Shows the loader
        $(".showOnLoadAddFood").hide();
        $(".loaderAddFood").show();
        
    	// Reset the fields
    	$scope.add_foodName = "";
    	$scope.add_foodType = "";
    	$scope.add_foodCalories = "";

    	/////////////
    	$http({
            url: centric02_basic+"/food/foodTypes",
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.foodTypes = success.data.foodType;
        	
        }, function(error){
        	console.log('Error testing');
        });
    	///////////////////////
		
	    // Hides the loader and shows the content
	    $(".loaderAddFood").hide();
	    $(".showOnLoadAddFood").show();
       
    };
    
    // Function to send a request for adding a new food
    $scope.addFoodSave=function(){

		// Shows the loader
    	$(".showOnLoadAddFood").hide();
        $(".loaderAddFood").show();
        
        // Gets the data the user inserted in the form
    	$scope.foodData.name=$scope.add_foodName;
    	$scope.foodData.calories=$scope.add_foodCalories;
    	$scope.foodData.foodType={};
    	$scope.foodData.foodType.category=$scope.add_foodType;
    	
        // Request to post the food the user wants to insert
    	$http({
            url: centric02_basic+"/food",
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: $scope.foodData
        }).then(function(success)
        {
        	// Reloads all the components in the page
        	$rootScope.loadRanking();
        	$rootScope.loadAddFood();
        	$rootScope.loadAddRecipe();
        	
            // Hides the loader and shows the content
            $(".loaderAddFood").hide();
            $(".showOnLoadAddFood").show();
            
        },function(error)
        {
            console.log('Error save food');
        });
    }
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadAddFood();
	
});




app.controller('addRecipeController', function ($scope,$rootScope, $http) {
	
	$scope.foodsList;
	$scope.recipeData = {};

	// Function to load the section 'Add Recipe'
	$rootScope.loadAddRecipe = function () {
		
		// Shows the loader
        $(".showOnLoadAddRecipe").hide();
        $(".loaderAddRecipe").show();

    	// Reset the fields
    	$scope.addRecipe_recipeName = "";
    	$scope.addRecipe_recipeDescription = "";

    	/////////////
    	$http({
            url: centric02_basic+"/food/all",
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {
        	
        	// Memorizes the data in the scope
        	$scope.foodsList = success.data.food;
        	console.log($scope.foodsList);
        	
        }, function(error){
        	console.log('Error testing');
        });
    	///////////////////////
		
	    // Hides the loader and shows the content
	    $(".loaderAddRecipe").hide();
	    $(".showOnLoadAddRecipe").show();
       
    };
    
    // Function to send a request for adding a new recipe
    $scope.addRecipeSave=function(){

		// Shows the loader
    	$(".showOnLoadAddRecipe").hide();
        $(".loaderAddRecipe").show();
        
        // Gets the data the user inserted in the form
    	$scope.recipeData.name=$scope.addRecipe_recipeName;
    	$scope.recipeData.description=$scope.addRecipe_recipeDescription;
    	
    	var arrayOfIngredients = document.getElementsByClassName('ingredient');
    	
    	$scope.recipeData.ingredients = [];
    	
    	for(i=0;i<arrayOfIngredients.length;i++) {
    		
        	$scope.recipeData.ingredients[i] = {};
        	$scope.recipeData.ingredients[i].idFood = arrayOfIngredients[i].options[arrayOfIngredients[i].selectedIndex].value;
        	
    	}

        // Request to post the recipe the user wants to insert
    	$http({
            url: centric02_basic+"/recipe",
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: $scope.recipeData
        }).then(function(success)
        {
        	// Reloads all the components in the page
        	$rootScope.loadRanking();
        	$rootScope.loadAddRecipe();
        	
            // Hides the loader and shows the content
            $(".loaderAddRecipe").hide();
            $(".showOnLoadAddRecipe").show();
            
        },function(error)
        {
            console.log('Error save food');
        });
    }
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadAddRecipe();
	
});




app.controller('addSentenceController', function ($scope,$rootScope, $http) {
	
	$scope.sentenceData = {}

	// Function to load the section 'Add Sentence'
	$rootScope.loadAddSentence = function () {
		
		// Shows the loader
        $(".showOnLoadAddSentence").hide();
        $(".loaderAddSentence").show();
        
        // Reset all the fields
    	$scope.add_sentenceText = "";
    	$scope.add_imageURL = "";
    	$scope.sentenceData.sentenceType = {};

        // Request to get the measure types available in the system
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

        	// Memorizes the data in the scope
        	$scope.measureTypes = success.data.measureType;

    	    // Hides the loader and shows the content
    	    $(".loaderAddSentence").hide();
    	    $(".showOnLoadAddSentence").show();
            
            
        }, function(error){
        	console.log('Error measure types');
        });
       
    };
    
    // Function to send a request for adding a new sentence
    $scope.addSentenceSave=function(){

		// Shows the loader
    	$(".showOnLoadAddSentence").hide();
        $(".loaderAddSentence").show();
        
        // Gets the data the user inserted in the form
    	$scope.sentenceData.text=$scope.add_sentenceText;
    	$scope.sentenceData.url=$scope.add_imageURL;
    	$scope.sentenceData.sentenceType={};
    	$scope.sentenceData.sentenceType.name=$scope.add_sentenceType;
    	if($scope.add_sentenceTypeMotive=="gain") 
        	$scope.sentenceData.sentenceType.motive=true;
    	else
    		$scope.sentenceData.sentenceType.motive=false;
    	console.log($scope.sentenceData);
        // Request to post the food the user wants to insert
    	$http({
            url: centric02_basic+"/sentence",
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: $scope.sentenceData
        }).then(function(success)
        {
        	// Reloads all the components in the page
        	$rootScope.loadRanking();
        	$rootScope.loadAddSentence();
        	
            // Hides the loader and shows the content
            $(".loaderAddSentence").hide();
            $(".showOnLoadAddSentence").show();
            
        },function(error)
        {
            console.log('Error save sentence');
        });
    }
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadAddSentence();
	
});




app.controller('dedicateSentenceController', function ($scope,$rootScope, $http) {
	
	$scope.sentenceData = {}
	$scope.measureTypes
	$scope.peopleList = {}
	$scope.usernames = []

	// Function to load the section 'Dedicate Sentence'
	$rootScope.loadDedicateSentence = function () {
		
		// Shows the loader
        $(".showOnLoadDedicateSentence").hide();
        $(".loaderDedicateSentence").show();

        // Reset all the fields
    	$scope.dedicate_toUser = "";
    	$scope.dedicate_sentenceType = "";
    	$scope.dedicate_sentenceTypeMotive = "";
    	
        // Request to get the measure types available in the system
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

        	// Memorizes the data in the scope
        	$scope.measureTypes = success.data.measureType;

    	    // Hides the loader and shows the content
    	    $(".loaderDedicateSentence").hide();
    	    $(".showOnLoadDedicateSentence").show();
            
            
        }, function(error){
        	console.log('Error measure types');
        });
		
        
        /////////////
    	$http({
            url: centric01_basic+"/people",
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            params: {
            }
        }).then(function(success) {

        	// Memorizes the data in the scope
        	$scope.peopleList = success.data.person;
        	
        	for(i=0;i<$scope.peopleList.length;i++) {
        		
        		$scope.usernames[i] = $scope.peopleList[i].username;
        		
        	}
        	console.log($scope.usernames);
        	
        }, function(error){
        	console.log('Error testing');
        });
    	///////////////////////
       
    };
    
    // Function to send a request for dedicate a sentence to a specific user
    $scope.dedicateSentenceSave=function(){

		// Shows the loader
    	$(".showOnLoadDedicateSentence").hide();
        $(".loaderDedicateSentence").show();
        
        // Gets the data the user inserted in the form
    	var toUser = $scope.dedicate_toUser;
    	var sentenceType = $scope.dedicate_sentenceType;
    	var sentenceTypeMotive = $scope.dedicate_sentenceTypeMotive;
    	
        // Request to post the food the user wants to insert
    	$http({
            url: centric02_basic+"/sentence/" + global_username + "/" + toUser + "/" + sentenceType + "/" + sentenceTypeMotive,
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            data: null
        }).then(function(success)
        {
        	// Reloads all the components in the page
        	$rootScope.loadAddSentence();
        	$rootScope.loadDedicateSentence();
        	
            // Hides the loader and shows the content
            $(".loaderDedicateSentence").hide();
            $(".showOnLoadDedicateSentence").show();
            
        },function(error)
        {
            console.log('Error dedicate sentence');
        });
    }
    

    // Calls instantly the function to load the component as soon as the controller is ready
    $rootScope.loadDedicateSentence();
	
});