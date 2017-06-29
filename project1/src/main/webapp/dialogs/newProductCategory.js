/**
 * 
 */
var app = angular.module('newProductCategory.controllers', []);

app.controller('newProductCategory', ['$rootScope','$scope', '$location','$http','$state',
	'managerService','$uibModalInstance',
  	function ($rootScope,$scope, $location,$http,$state,managerService,$uibModalInstance) {
	      	    
		$scope.close=function(){
			$uibModalInstance.close();
		}
		
		if($scope.productCategory.code==undefined){
			//$scope.buyerCategory.consumptionTresholds=[];
			
			managerService.getProductCategories().then(
				function(response){
					$scope.categories=response.data;
				}
			);
		}else{
			$uibModalInstance.rendered.then(function(){
				document.getElementById("code").disabled=true;
				document.getElementById("button1").textContent="edit";
				managerService.getAvailibleProductCategories($scope.productCategory).then(
					function(response){
						$scope.categories=response.data;
					}
				);
			});
		}
		
		
		$scope.create = function(){
			
			$scope.productCategory.code = $scope.productCategory.code.trim();
			$scope.productCategory.name = $scope.productCategory.name.trim();
			if($scope.productCategory.maximumDiscount > 100 ||
					$scope.productCategory.maximumDiscount<0) return;
			
			if($scope.productCategory.aboveCategory!=null)
			$scope.productCategory.aboveCategory=JSON.parse($scope.productCategory.aboveCategory);
			
			if(document.getElementById("button1").textContent=="edit"){
				managerService.editPC($scope.productCategory).then(function(response){
					$uibModalInstance.close();
				});	
			}else{
				managerService.createPC($scope.productCategory).then(function(response){
					if(response.status==202){
						alert("postoji vec product category s tim kodom");
						return;
					}
					$uibModalInstance.close();
				});
			}
		};

}]);