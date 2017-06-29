/**
 * 
 */
var app = angular.module('confirmReceipt.controllers', []);

app.controller('confirmReceipt', ['$rootScope','$scope', '$location','$http','$state',
	'buyerService','$uibModalInstance',
  	function ($rootScope,$scope, $location,$http,$state,buyerService,$uibModalInstance) {
	      	    
		$scope.close=function(){
			$uibModalInstance.close();
		}
		
		$scope.create=function(receipt){
			if($scope.receipt.buyer.prizePoints<$scope.receipt.numberOfSpentPoints){
				alert(" nemate toliko poena free");
				return;
			}
			buyerService.preBuy($scope.receipt);
			$uibModalInstance.close();
		}

}]);