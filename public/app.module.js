var _ = require("underscore");

var angular = require("angular");
require("angular-route");
require("angular-ui-bootstrap");
require("satellizer");

angular
.module("FarmersMarket",[
  "ngRoute",
  'ui.bootstrap',
  'admin.module' ,
  'buyers.module',
  'buyers-profile.module',
  'farmers.module',
  'farmers-profile.module',
  'satellizer'
])
.config(function($routeProvider) {
  $routeProvider
    .when('/',{
      templateUrl: "home/views/home.html",
      controller: "HomeController"
    })
  
})
.config(function($authProvider) {
  $authProvider.loginUrl = '/login';
  $authProvider.signupUrl = '/users';
})
.constant('_', window._)
.run(function ($rootScope) {
   $rootScope._ = window._;
});



require('./admin');
require('./buyers');
require('./buyers-profile');
require('./farmers-profile');
require('./home');
require('./farmers');
require('./auth');
