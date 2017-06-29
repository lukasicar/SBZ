/**
 * 
 */
var app = angular.module('profile.controllers', []);

app.controller('profileController', ['$rootScope','$scope', '$location','$http','$state',
	'sellerService','$uibModal','buyerService',
  	function ($rootScope,$scope, $location,$http,$state,sellerService,$uibModal
  			,buyerService) {
	    alert("buyer");
	    
	    $scope.logOut=function(){
	    	sellerService.logOut();
	    	sessionStorage.clear();
	    }
	    
	    
	    
	    
	    
	    init();
		function init(){
			if(sessionStorage.user!=undefined){
				$scope.user = JSON.parse(sessionStorage.user);
				$scope.user.registrationDate=new Date($scope.user.registrationDate);
				if($scope.user.role=='MANAGER')
					$state.transitionTo('manager');
				else if($scope.user.role=='SELLER')
					$state.transitionTo('seller');
			}else{
				$state.transitionTo('login');
			}
			
			
			buyerService.getMyBuyer().then(
					function(response) {
						
						$scope.user=response.data;
						
					});
			
			
		}
		
	    
	   
}]);