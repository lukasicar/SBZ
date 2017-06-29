/**
 * 
 */
var app = angular.module('newProduct.controllers', []);

app.controller('newProduct', ['$rootScope','$scope', '$location','$http','$state',
	'sellerService','$uibModalInstance','managerService',
  	function ($rootScope,$scope, $location,$http,$state,sellerService,$uibModalInstance
  			,managerService) {
	      	    
		$scope.close=function(){
			$uibModalInstance.close();
		}
		
		$scope.product.recordCreationDate=new Date($scope.product.recordCreationDate);
		
		
		managerService.getProductCategories().then(
				function(response){
					$scope.categories=response.data;
				}
			);
		
		if($scope.product.code==undefined){
			
		}else{
			$uibModalInstance.rendered.then(function(){
				document.getElementById("code").disabled=true;
				document.getElementById("button1").textContent="edit";
			});
		}
		$scope.create = function(){
			
			$scope.product.code = $scope.product.code.trim();
			$scope.product.name = $scope.product.name.trim();
			
			if($scope.product.minimumInStock>$scope.product.inStock)
				return;
	
			
			if(document.getElementById("button1").textContent=="edit"){
				sellerService.editProduct($scope.product).then(function(response){
					$uibModalInstance.close();
				});	
			}else{
				sellerService.createProduct($scope.product).then(function(response){
					if(response.status==202){
						alert("postoji vec product category s tim kodom");
						return;
					}
					$uibModalInstance.close();
				});
			}
		};

}]);