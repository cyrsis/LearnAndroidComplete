'use strict';

// Declare app level module which depends on views, and components
var app=angular.module('contacts', ['ngRoute','firebase','toastr','myApp.contacts']);
app.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  $routeProvider.when('/login', {templateUrl: 'login/login.html',controller: 'loginCtrl' });
  $routeProvider.when('/dashboard', {templateUrl: 'dashboard/dashboard.html',controller: 'DashCtrl' });
    $routeProvider.when('/dashboard_category', {templateUrl: 'dashboard/dashboard_category.html',controller: 'DashCatCtrl' });
    $routeProvider.when('/dashboard_add_category', {templateUrl: 'dashboard/dashboard_add_category.html',controller: 'DashCatCtrl' });
    $routeProvider.when('/dashboard_recipes', {templateUrl: 'dashboard/dashboard_recipes.html',controller: 'RecCtrl' });
    $routeProvider.when('/dashboard_add_recipe', {templateUrl: 'dashboard/dashboard_add_recipe.html',controller: 'DashRecCtrl' });
    $routeProvider.when('/dashboard_order', {templateUrl: 'dashboard/dashboard_order.html',controller: 'OrderCtrl' });
    $routeProvider.when('/dashboard_users', {templateUrl: 'dashboard/dashboard_users.html',controller: 'UsersCtrl' });
  $routeProvider.when('/dashboard_slider', {templateUrl: 'dashboard/dashboard_slider.html',controller: 'SlidCtrl' });
    $routeProvider.when('/dashboard_add_slider', {templateUrl: 'dashboard/dashboard_add_slider.html',controller: 'DashSlCtrl' });
   
  $routeProvider.otherwise({redirectTo: '/login'});
}]);

app.run(function($rootScope, $location, loginService){
	var routespermission=['/dashboard','/dashboard_category','/dashboard_add_category','/dashboard_order','/dashboard_users','/dashboard_recipes','/dashboard_add_recipe','/dashboard_add_slider','/dashboard_slider'];  //route that require login
	$rootScope.$on('$routeChangeStart', function(){
		if( routespermission.indexOf($location.path()) !=-1)
		{
			var connected=loginService.islogged();
			connected.then(function(msg){
				if(!msg.data) $location.path('/login');
			});
		}
	});
    
    });