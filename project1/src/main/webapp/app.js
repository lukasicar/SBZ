/**
 * 
 */
angular.module('app', ['ui.router','ui.bootstrap','login.controllers','login.services',
	'newBuyerCategory.controllers','manager.controllers','manager.services',
	'newProductCategory.controllers','newActionEvent.controllers',
	'seller.services','seller.controllers','newProduct.controllers',
	'buyer.controllers','history.controllers','profile.controllers',
	'buyer.services','confirmReceipt.controllers'
	]).config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/login');
    
    $stateProvider
    
    .state('login', {
    	url : '/login',
      	templateUrl : 'pages/login.html',
      	controller : 'loginController'
     })
     
     .state('register', {
    	url : '/register',
      	templateUrl : 'pages/register.html',
      	controller : 'loginController'
     })
     .state('manager', {
    	url : '/manager',
      	templateUrl : 'pages/manager.html',
      	controller : 'managerController'
     })
     .state('seller', {
    	url : '/seller',
      	templateUrl : 'pages/seller.html',
      	controller : 'sellerController'
     })
     .state('buyer', {
    	url : '/buyer',
      	templateUrl : 'pages/buyer.html',
      	controller : 'buyerController'
     })
     .state('profile', {
    	url : '/profile',
      	templateUrl : 'pages/profile.html',
      	controller : 'profileController'
     })
     .state('history', {
    	url : '/history',
      	templateUrl : 'pages/history.html',
      	controller : 'historyController'
     })
});