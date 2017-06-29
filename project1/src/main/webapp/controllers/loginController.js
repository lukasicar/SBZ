/**
 * 
 */
var app = angular.module('login.controllers', []);

app.controller('loginController', ['$rootScope','$scope','loginService', '$location','$http','$state',
  	function ($rootScope,$scope, loginService, $location,$http,$state) {
	
		
		init();
		function init(){
			//alert(sessionStorage.user);
			//alert(sessionStorage.user==undefined);
			if(sessionStorage.user!=undefined){
				$scope.user = JSON.parse(sessionStorage.user);
				//alert($scope.user.role);
				if($scope.user.role=='MANAGER')
					$state.transitionTo('manager');
				else if($scope.user.role=='SELLER')
					$state.transitionTo('seller');
				else if($scope.user.role=='BUYER')
					$state.transitionTo('buyer');
			}
		}
			
	
		$scope.submitLogin = function () {
			loginService.logIn($scope.user).then(
				function (response) {
                    if(response.status==202){
                    	alert("pogresno korisnicko ime ili sifra");
                    	return;
                    }else{         
                    	sessionStorage.user=JSON.stringify(response.data);
                    	if(response.data.role=='MANAGER')
                    		$state.transitionTo("manager");
                    	else if(response.data.role=='SELLER')
                    		$state.transitionTo('seller');
                    	else if(response.data.role=='BUYER')
        					$state.transitionTo('buyer');
                    }
				}
			)
		}
		
		$scope.submitRegistration = function () {  
			if($("#pass").val() != $("#passRepeat").val()){
				alert("Password does not match the confirm password");
				return;
			}
			loginService.register($scope.user).then(function(response){
				if(response.status==202){
					alert("Korisnik sa datim username-om vec postoji");
				}
			});
		};
			
		
		$scope.opsa = function () {  
			alert("nestoo");/*
			loginService.arest().then(
				function (response) {
					alert("eoeo");
					$state.go('chatPage');
                }
                
            ); 	*/
		};
		
	    
}]);