angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider



  .state('tabsController', {
    url: '/page1',
    templateUrl: 'templates/tabsController.html',
    abstract:true
  })

  .state('tabsController.login', {
    url: '/page5',
    views: {
      'tab1': {
        templateUrl: 'templates/login.html',
        controller: 'loginCtrl'
      }
    }
  })

  .state('tabsController.signup', {
    url: '/page6',
    views: {
      'tab3': {
        templateUrl: 'templates/signup.html',
        controller: 'signupCtrl'
      }
    }
  })

  .state('menu', {
      url: '/page7',
      templateUrl: 'templates/menu.html',
      controller: 'menuCtrl'
    })
.state('list', {
      url: '/page19/:id',
      templateUrl: 'templates/product_list.html',
      controller: 'productCtrl'
    })

  .state('categories', {
    url: '/page8/:id',
    templateUrl: 'templates/categories.html',
    controller: 'categoriesCtrl'
  })

  .state('myCart', {
    url: '/page9',
    templateUrl: 'templates/myCart.html',
    controller: 'myCartCtrl'
  })

  .state('lastOrders', {
    url: '/page10',
    templateUrl: 'templates/lastOrders.html',
    controller: 'lastOrdersCtrl'
  })

  
  .state('settings', {
    url: '/page12',
    templateUrl: 'templates/settings.html',
    controller: 'settingsCtrl'
  })

  .state('recipeView', {
    url: '/page13/:pid',
    templateUrl: 'templates/recipeView.html',
    controller: 'recipeViewCtrl'
  })

  .state('checkout', {
    url: '/page16',
    templateUrl: 'templates/checkout.html',
    controller: 'checkoutCtrl'
  })

  

$urlRouterProvider.otherwise('/page1/page5')



});
