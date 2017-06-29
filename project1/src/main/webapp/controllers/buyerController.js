/**
 * 
 */
var app = angular.module('buyer.controllers', []);

app.controller('buyerController', ['$rootScope','$scope', '$location','$http','$state',
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
				if($scope.user.role=='MANAGER')
					$state.transitionTo('manager');
				else if($scope.user.role=='SELLER')
					$state.transitionTo('seller');
			}else{
				$state.transitionTo('login');
			}
			
			if(sessionStorage.korpa==undefined){
				$scope.korpa=new Object();
				$scope.korpa.products=[];
				sessionStorage.korpa=JSON.stringify($scope.korpa);
			}else{
				$scope.korpa=JSON.parse(sessionStorage.korpa);
			}
			
			sellerService.getProducts().then(
					function (response) {
						$scope.products=response.data;
					});
			
			buyerService.getMyBuyer().then(
					function(response) {
						$scope.user=response.data;
					});
			
			
		}
		
		$scope.profile=function(){
			$state.transitionTo('profile');
		}
		$scope.history=function(){
			$state.transitionTo('history');
		}
		
		$scope.addToCart=function(product,amount){
			$scope.korpa=JSON.parse(sessionStorage.korpa);
			if(product.inStock<amount){
				alert("nema toliko na lageru");
				return;
			}else{
				//product.inStock-=amount;
			}
			dodajArtikl(product,amount);
			sessionStorage.korpa=JSON.stringify($scope.korpa);
		}
		
		function dodajArtikl(product,amount){
			for(var i=0; i<$scope.korpa.products.length; i++){
				if($scope.korpa.products[i].code == product.code){
					$scope.korpa.products[i].kolicina += amount;
					return;
				}
			}
			$scope.korpa.products.push(product);
			$scope.korpa.products[$scope.korpa.products.length-1].kolicina=amount;
		}
		
		$scope.removeFromCart=function(product){
			$scope.korpa=JSON.parse(sessionStorage.korpa);
			for(var i=0; i<$scope.korpa.products.length; i++){
				if($scope.korpa.products[i].code == product.code){
					$scope.korpa.products.splice(i,1);
					break;
				}
			}
			sessionStorage.korpa=JSON.stringify($scope.korpa);
		}
	    
		$scope.createBill=function(){
			var items=getItems();
			if(items.length==0){
				alert("prazna korpa ili nema toliko resursa");
				return;
			}
			var object=new Object();
			object.date=new Date();
			object.buyer=$scope.user;
			object.items=items;
			buyerService.createBill(object).then(function(response){
				var scope = $scope.$new(true);
		        scope.buyerService = buyerService;
		        scope.receipt=response.data;
		        $uibModal.open({
		            animation: true,
		            templateUrl: 'dialogs/confirmReceipt.html',
		            controller: 'confirmReceipt',
		            size: 'lg',
		            scope: scope,
		            backdrop : false
		        }).closed.then(function(){
		        	sellerService.getProducts().then(
							function (response) {
								$scope.products=response.data;
							});
					
					buyerService.getMyBuyer().then(
							function(response) {
								$scope.user=response.data;
							});
		        });
			});
		}
		
		function getItems(){
			var retVal = [];
			for(var i=0; i<$scope.korpa.products.length; i++){
				if(findItem($scope.korpa.products[i].code).inStock<
						$scope.korpa.products[i].kolicina){
					alert("prazna korpa ili nema toliko resursa");
					return [];
				}
				var elem=new Object();
				elem.ordinalNumber=i;
				elem.product=findItem($scope.korpa.products[i].code);
				elem.price=$scope.korpa.products[i].price;
				elem.amount=$scope.korpa.products[i].kolicina;
				elem.originalSumPrice=$scope.korpa.products[i].price*
				$scope.korpa.products[i].kolicina;
				
				retVal.push(elem);
			}
			return retVal;
		}
	   
		function findItem(code){
			for(var i=0;i<$scope.products.length;i++){
				if($scope.products[i].code==code)
					return $scope.products[i];
			}
		}
		
		
}]);