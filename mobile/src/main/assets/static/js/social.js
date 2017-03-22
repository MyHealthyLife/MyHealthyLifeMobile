
var availableTags = ["No option available"];
var availableUsernames = ["No username present"];
var counterFoodsInRecipe = 2;

function autoCompleteModules(){
	
	var measures = $('[ng-controller="addFoodController"]').scope().foodTypes;
	
	if(measures.length!=0) {
		availableTags = [];
	}
	
	for(i=0;i<measures.length;i++) {
		
		availableTags.push(measures[i].category);
		
	}
	

	var usernamesFromController = $('[ng-controller="dedicateSentenceController"]').scope().usernames;

	if(usernamesFromController.length!=0) {
		availableUsernames = [];
	}
	
	for(i=0;i<usernamesFromController.length;i++) {
		
		availableUsernames.push(usernamesFromController[i]);
		
	}
	
	//console.log(availableUsernames);
	//console.log(availableTags);
	
}

$(document).ready(function() {
	setInterval(function(){
	    autoCompleteModules();
	  
	$("#foodTypeAuto").autocomplete({
	      source: availableTags
	    });
	$("#dedicateToUserAuto").autocomplete({
	      source: availableUsernames
	    });
	  }, 3000)

	    $("#hiddenCloneRecipe").hide();

	$("#num").click(function () {
	  $(".Box:first").clone().appendTo("#form");
	  
		$("#boxToClone").attr("class", String(counterFoodsInRecipe));
		$("#boxToClone").attr("id", "view");

		$("#foodSelector").attr("ng-model", "addRecipe_foodName" + String(counterFoodsInRecipe));
		$("#foodSelector").attr("class", "form-control ingredient");
		$("#foodSelector").attr("id", "foodSelectorView");
		
		counterFoodsInRecipe++;
	});
	


	$("#rmv").click(function () {
		 counterFoodsInRecipe--;
		 $('.' + String(counterFoodsInRecipe)).remove();
	});
	
});
    