/**
 * 
 */
var app = angular.module('seller.controllers', []);

app.controller('sellerController', ['$rootScope','$scope', '$location','$http','$state',
	'sellerService','$uibModal','buyerService',
  	function ($rootScope,$scope, $location,$http,$state,sellerService,$uibModal
  			,buyerService) {
	    alert("seller");
	    
	    $scope.logOut=function(){
	    	sellerService.logOut();
	    	sessionStorage.clear();
	    }
	    
	    
	    $scope.buyerCategories=[];
	    $scope.receipts=[];
	    
	    init();
		function init(){
			if(sessionStorage.user!=undefined){
				$scope.user = JSON.parse(sessionStorage.user);
				if($scope.user.role=='MANAGER')
					$state.transitionTo('manager');
			}else{
				$state.transitionTo('login');
			}
			
			
			sellerService.getProducts().then(
					function (response) {
						$scope.products=response.data;
					});
			
			buyerService.getReceipts().then(
					function (response) {
						$scope.receipts=[];
						for(var x in response.data){
							response.data[x].date=new Date(response.data[x].date);
							$scope.receipts.push(response.data[x]);
						}
					});
		}
		
		
//////////PRODUCT
		$scope.editProduct = function (product) {
	        var scope = $scope.$new(true);
	        scope.sellerService = sellerService;
	        scope.product=product;
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newProduct.html',
	            controller: 'newProduct',
	            size: 'lg',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	sellerService.getProducts().then(
						function (response) {
							$scope.products=[];
							$scope.products=response.data;
						});
			});
	    };
	    
	    $scope.newProduct = function () {
	        var scope = $scope.$new(true);
	        scope.sellerService = sellerService;
	        scope.product=new Object();
	        $uibModal.open({
	            animation: true,
	            templateUrl: 'dialogs/newProduct.html',
	            controller: 'newProduct',
	            size: 'lg',
	            scope: scope,
	            backdrop : false
	        }).closed.then(function(){
	        	sellerService.getProducts().then(
						function (response) {
							$scope.products=[];
							$scope.products=response.data;
						});
			});
	    };
	    
	    
	    $scope.deleteProduct = function (product) {
	        sellerService.deleteProduct(product).then(
	        		function(){
	        			sellerService.getProducts().then(
	    						function (response) {
	    							$scope.products=[];
	    							$scope.products=response.data;
	    						});
	        		});
	    };

	    
	    $scope.fillTheStocks = function (product) {
	        sellerService.fillTheStocks(product).then(
	        		function(){
	        			sellerService.getProducts().then(
	    						function (response) {
	    							$scope.products=[];
	    							$scope.products=response.data;
	    						});
	        		});
	    };
	    
	   
	    ////////////////RECEIPT
	    
	    $scope.orderReceipt = function (product) {
	        buyerService.buy(product).then(
	        		function(response){
	        			if(response.status==202){
	        				alert("nema dovoljno na lageru kolicine");
	        				return;
	        			}
	        			buyerService.getReceipts().then(
	        					function (response) {
	        						$scope.receipts=[];
	        						for(var x in response.data){
	        							response.data[x].date=new Date(response.data[x].date);
	        							$scope.receipts.push(response.data[x]);
	        						}
	        					});
	        		});
	    };
	    
	    $scope.removeReceipt = function (product) {
	        buyerService.removeR(product).then(
	        		function(){
	        			buyerService.getReceipts().then(
	        					function (response) {
	        						$scope.receipts=[];
	        						for(var x in response.data){
	        							response.data[x].date=new Date(response.data[x].date);
	        							$scope.receipts.push(response.data[x]);
	        						}
	        					});
	        		});
	    };
	    
}]);