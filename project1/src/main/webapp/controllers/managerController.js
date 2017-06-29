/**
 * 
 */
var app = angular.module('manager.controllers', []);

app.controller('managerController', ['$rootScope','$scope', '$location','$http','$state',
	'managerService','$uibModal',
  	function ($rootScope,$scope, $location,$http,$state,managerService,$uibModal) {
	    //alert("bosnojabukou cvijetu");
	    
	    $scope.logOut=function(){
	    	managerService.logOut();
	    	sessionStorage.clear();
	    }
	    
	    
	    $scope.buyerCategories=[];
	    
	    
	    init();
		function init(){
			//alert(sessionStorage.user);
			//alert(sessionStorage.user==undefined);
			if(sessionStorage.user!=undefined){
				$scope.user = JSON.parse(sessionStorage.user);
				alert($scope.user.role);
				if($scope.user.role=='SELLER')
					$state.transitionTo('seller');
			}else{
				$state.transitionTo('login');
			}
			
			
			managerService.getBuyerCategories().then(
					function (response) {
						for(var x in response.data){
							 $scope.buyerCategories.push(response.data[x]);
						}
						
					});
			managerService.getProductCategories().then(
					function(response){
						for(var x in response.data){
							$scope.productCategories.push(response.data[x]);
						}
					});
			managerService.getActionEvents().then(
					function(response){
						for(var x in response.data){
							response.data[x].endDate=new Date(response.data[x].endDate);
							$scope.actionEvents=response.data;
						}
					});
		}
		
		
//////////BUYER CATEGORY
		$scope.editBC = function (buyerCategory) {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.buyerCategory=buyerCategory;
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newBuyerCategory.html',
	            controller: 'newBuyerCategory',
	            size: 'lg',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getBuyerCategories().then(
						function (response) {
							 $scope.buyerCategories=[];
							for(var x in response.data)
								 $scope.buyerCategories.push(response.data[x]);
						});
			});
	    };
	    
	    $scope.createNewBC = function () {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.buyerCategory=new Object();
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newBuyerCategory.html',
	            controller: 'newBuyerCategory',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getBuyerCategories().then(
						function (response) {
							 $scope.buyerCategories=[];
							for(var x in response.data)
								 $scope.buyerCategories.push(response.data[x]);
						});
			});
	    };
	    
	    
	    $scope.deleteBC = function (buyerCategory) {
	        managerService.deleteBC(buyerCategory).then(
	        		function(){
	        managerService.getBuyerCategories().then(
					function (response) {
						$scope.buyerCategories=[];
						for(var x in response.data)
							 $scope.buyerCategories.push(response.data[x]);
					});
	        	});
	    };
////////////////////////PRODUCT CATEGORY
	    
	    $scope.productCategories=[];
	    
	    $scope.editPC = function (productCategory) {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.productCategory=productCategory;
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newProductCategory.html',
	            controller: 'newProductCategory',
	            size: 'lg',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getProductCategories().then(
						function(response){
							$scope.productCategories=[];
							for(var x in response.data){
								$scope.productCategories.push(response.data[x]);
							}
						});
			});
	    };
	    
	    $scope.createNewPC = function () {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.productCategory=new Object();
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newProductCategory.html',
	            controller: 'newProductCategory',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getProductCategories().then(
						function(response){
							$scope.productCategories=[];
							for(var x in response.data){
								$scope.productCategories.push(response.data[x]);
							}
						});
			});
	    };
	    
	    
	    $scope.deletePC = function (productCategory) {
	        managerService.deletePC(productCategory).then(
	        		function(){
	        			managerService.getProductCategories().then(
	        					function(response){
	        						$scope.productCategories=[];
	        						for(var x in response.data){
	        							$scope.productCategories.push(response.data[x]);
	        						}
	        					});
	        	});
	    };
	    /////////////////////////ACTION EVENTS
	    $scope.actionEvents=[];
	    
	    $scope.editAE = function (actionEvent) {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.actionEvent=actionEvent;
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newActionEvent.html',
	            controller: 'newActionEvent',
	            size: 'lg',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getActionEvents().then(
						function(response){
							$scope.actionEvents=[];
							for(var x in response.data){
								response.data[x].endDate=new Date(response.data[x].endDate);
								$scope.actionEvents.push(response.data[x]);
							}
						});
			});
	    };
	    
	    $scope.createNewAE = function () {
	        var scope = $scope.$new(true);
	        scope.managerService = managerService;
	        scope.actionEvent=new Object();
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newActionEvent.html',
	            controller: 'newActionEvent',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	managerService.getActionEvents().then(
						function(response){
							$scope.actionEvents=[];
							for(var x in response.data){
								response.data[x].endDate=new Date(response.data[x].endDate);
								$scope.actionEvents.push(response.data[x]);
							}
						});
			});
	    };
	    
	    
	    $scope.deleteAE = function (actionEvent) {
	        managerService.deleteAE(actionEvent).then(
	        		function(){
	        			managerService.getActionEvents().then(
	        					function(response){
	        						$scope.actionEvents=[];
	        						for(var x in response.data){
	        							response.data[x].endDate=new Date(response.data[x].endDate);
	        							$scope.actionEvents.push(response.data[x]);
	        						}
	        					});
	        	});
	    };
}]);