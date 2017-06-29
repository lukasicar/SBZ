/**
 * 
 */
var services = angular.module('buyer.services', ['ngResource']);

//var baseUrl = 'http://localhost\\:8080';

services.service('buyerService', ['$http', function($http){

	this.logOut = function(){
		return $http.get("/login/logOut");
	}
	this.createBill=function(object){
		return $http.post("/buyer/createBill",object);
	}
	this.getMyBuyer=function(){
		return $http.get("/buyer/getMyBuyer");
	}
	
	this.buy=function(receipt){
		return $http.post("/buyer/buy",receipt);
	}
	
	this.preBuy=function(receipt){
		return $http.post("/buyer/preBuy",receipt);
	}
	
	this.getReceipts=function(){
		return $http.get("/buyer/receipts");
	}
	
	this.removeR=function(receipt){
		return $http.post("/buyer/removeR",receipt);
	}
}]);