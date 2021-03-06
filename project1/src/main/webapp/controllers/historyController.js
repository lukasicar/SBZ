/**
 * 
 */
var app = angular.module('history.controllers', []);

app.controller('historyController', ['$rootScope','$scope', '$location','$http','$state',
	'sellerService','$uibModal','buyerService',
  	function ($rootScope,$scope, $location,$http,$state,sellerService,$uibModal
  			,buyerService) {
	    alert("buyer");
	    
	    
	   
	    
	    
	    init();
		function init(){
			if(sessionStorage.user!=undefined){
				$scope.user = JSON.parse(sessionStorage.user);
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
		$scope.receipts=[];
		buyerService.getMyReceipts().then(
				function (response) {
					$scope.receipts=[];
					for(var x in response.data){
						response.data[x].date=new Date(response.data[x].date);
						$scope.receipts.push(response.data[x]);
					}
				});
	   
}]);