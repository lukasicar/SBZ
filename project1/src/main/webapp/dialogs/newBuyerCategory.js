/**
 * 
 */
var app = angular.module('newBuyerCategory.controllers', []);

app.controller('newBuyerCategory', ['$rootScope','$scope', '$location','$http','$state',
	'managerService','$uibModalInstance',
  	function ($rootScope,$scope, $location,$http,$state,managerService,$uibModalInstance) {
	      	    
		$scope.close=function(){
			$uibModalInstance.close();
		}
		
		$scope.newTreshold = {id: null, consumptionLow:null, consumptionHigh:null, percentage:null};
		
		if($scope.buyerCategory.consumptionTresholds==undefined){
			$scope.buyerCategory.consumptionTresholds=[];
		}else{
			$uibModalInstance.rendered.then(function(){
				document.getElementById("code").disabled=true;
				document.getElementById("button1").textContent="edit";
			});
		}
		
	    $scope.addTreshold = function(){
			if($scope.newTreshold.consumptionLow == null || 
					$scope.newTreshold.consumptionHigh == null || 
					$scope.newTreshold.percentage== null)return;
			if($scope.newTreshold.consumptionLow < 0 || 
					$scope.newTreshold.consumptionHigh < 0 || 
					$scope.newTreshold.percentage < 0) return;
			
			if($scope.newTreshold.percentage > 100) return;

			$scope.buyerCategory.consumptionTresholds.push({id:$scope.newTreshold.id, 
				consumptionLow:$scope.newTreshold.consumptionLow, 
				consumptionHigh:$scope.newTreshold.consumptionHigh, 
				percentage:$scope.newTreshold.percentage});
			
			$scope.newTreshold = {id: null, consumptionLow:null, consumptionHigh:null, percentage:null};
		};
		
		$scope.deleteTreshold = function(t){
			var x=$scope.buyerCategory.consumptionTresholds.indexOf(t);
			$scope.buyerCategory.consumptionTresholds.splice(x,1);
		};
		
		$scope.create = function(){
			if($scope.buyerCategory.code.trim().length > 1){
				//alert("Dozvoljen broj karaktera za oznaku kategorije je: 1");
				//return;
			}else{
				$scope.buyerCategory.code = $scope.buyerCategory.code.trim();
				$scope.buyerCategory.name = $scope.buyerCategory.name.trim();
			}
			
			if(document.getElementById("button1").textContent=="edit"){
				managerService.editBC($scope.buyerCategory).then(function(response){
					$uibModalInstance.close();
				});	
			}else{
				managerService.createBC($scope.buyerCategory).then(function(response){
					if(response.status==202){
						alert("postoji vec buyer category s tim kodom");
						return;
					}
					$uibModalInstance.close();
				});
			}
		};

}]);