/**
 * 
 */
var services = angular.module('manager.services', ['ngResource']);

//var baseUrl = 'http://localhost\\:8080';

services.service('managerService', ['$http', function($http){

	this.logOut = function(){
		return $http.get("/login/logOut");
	}
	/////////////BUYER CATEGORIES
	this.getBuyerCategories=function(){
		return $http.get("/buyerCategory/getAll");
	}
	
	this.deleteBC=function(buyerCategory){
		return $http.post("/buyerCategory/delete",buyerCategory);
	}
	
	this.createBC=function(buyerCategory){
		return $http.post("/buyerCategory/new",buyerCategory);
	}
	this.editBC=function(buyerCategory){
		return $http.put("/buyerCategory/edit",buyerCategory);
	}
	///////////////PRODUCT CATEGORIES
	this.deletePC=function(productCategory){
		return $http.post("/productCategory/delete",productCategory);
	}
	
	this.createPC=function(productCategory){
		return $http.post("/productCategory/new",productCategory);
	}
	this.editPC=function(productCategory){
		return $http.put("/productCategory/edit",productCategory);
	}
	this.getProductCategories=function(){
		return $http.get("/productCategory/getAll");
	}
	this.getAvailibleProductCategories=function(productCategory){
		return $http.post("/productCategory/getAvailible",productCategory);
	}
	////////////////ACTION EVENTS
	this.deleteAE=function(actionEvent){
		return $http.post("/actionEvent/delete",actionEvent);
	}
	
	this.createAE=function(actionEvent){
		return $http.post("/actionEvent/new",actionEvent);
	}
	this.editAE=function(actionEvent){
		return $http.put("/actionEvent/edit",actionEvent);
	}
	this.getActionEvents=function(){
		return $http.get("/actionEvent/getAll");
	}
	this.getActionEventCategories=function(actionEvent){
		return $http.post("/actionEvent/getCategories",actionEvent);
	}
	
}]);