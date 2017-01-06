'use strict';

angular.module('myApp.contacts', ['ngRoute'])

.controller('DashCtrl', ['$scope','$firebaseArray','loginService', function($scope,$firebaseArray,loginService) {
   
   var ref = new Firebase('https://<your-firebase-app> .firebaseio.com/orders')
    $scope.orders = $firebaseArray(ref);
    var uref = new Firebase('https://<your-firebase-app> .firebaseio.com/users')
     
    $scope.users = $firebaseArray(uref);
}]);