/**
 * 
 */
var services = angular.module('seller.services', ['ngResource']);

//var baseUrl = 'http://localhost\\:8080';

services.service('sellerService', ['$http', function($http){

	this.logOut = function(){
		return $http.get("/login/logOut");
	}
	/////////PRODUCTS
	this.getProducts=function(){
		return $http.get("/product/getAll");
	}
	
	this.deleteProduct=function(product){
		return $http.post("/product/delete",product);
	}
	
	this.createProduct=function(product){
		return $http.post("/product/create",product);
	}
	
	this.editProduct=function(product){
		return $http.put("/product/edit",product);
	}
	
	this.fillTheStocks=function(product){
		return $http.put("/product/fillTheStocks",product);
	}
}]);