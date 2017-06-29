/**
 * 
 */
var app = angular.module('newActionEvent.controllers', []);

app.controller('newActionEvent', ['$rootScope','$scope', '$location','$http','$state',
	'managerService','$uibModalInstance',
  	function ($rootScope,$scope, $location,$http,$state,managerService,$uibModalInstance) {
	      	    
		$scope.close=function(){
			$uibModalInstance.close();
		}
		
		
		$scope.actionEvent.endDate=new Date($scope.actionEvent.endDate);
		$scope.actionEvent.beginDate=new Date($scope.actionEvent.beginDate);
		//alert($scope.actionEvent.categories);

		managerService.getProductCategories().then(
				function(response){
					$scope.categories=response.data;
				}
			);
		if($scope.actionEvent.code==undefined){	
		}else{
			$uibModalInstance.rendered.then(function(){
				document.getElementById("code").disabled=true;
				document.getElementById("button1").textContent="edit";
			});
		}
		
		
		$scope.create = function(){
			
			$scope.actionEvent.code = $scope.actionEvent.code.trim();
			$scope.actionEvent.name = $scope.actionEvent.name.trim();
			if($scope.actionEvent.discount > 100 ||
					$scope.actionEvent.discount<0) return;
			
			if($scope.actionEvent.beginDate>$scope.actionEvent.endDate)return;

			//$scope.actionEvent.categories=JSON.parse($scope.actionEvent.categories);
			//alert($scope.actionEvent.categories);
			//return;
			
			if(document.getElementById("button1").textContent=="edit"){
				managerService.editAE($scope.actionEvent).then(function(response){
					$uibModalInstance.close();
				});	
			}else{
				managerService.createAE($scope.actionEvent).then(function(response){
					if(response.status==202){
						alert("postoji vec action event s tim kodom");
						return;
					}
					$uibModalInstance.close();
				});
			}
		};

}]);