var _ = require("underscore");

angular
.module("FarmersMarket")
.controller("HomeController", HomeController);

HomeController.$inject = ["$scope", "$http", "$location", "$q", "$rootScope", "HomeService"];

function HomeController($scope,$http,$location,$q,$rootScope,HomeService) {
  // HomeService.getUser()
  // .then(function(data) {
  //   console.log("THIS SHOULD BE USERS", users);
  //
  // })

  $scope.getUser = function(user) {
    console.log("NOTHIGN IS HAPPENING?");
    HomeService.getUser(user)
    .then(function(data){
      console.log('SUCCESS: ', data);
      $location.path('/users');
    },function error(err) {
      console.log("nope: ", err)
    })
  }

  $scope.createFarmer = function(user) {
    console.log("NOTHIGN IS HAPPENING?");

    HomeService.createFarmer(user)
      .then(function(data) {
        console.log('SUCCESS: ', data);
        $location.path('/farmers');
      },function error(err) {
        console.log("FUCK!: ", err);
      })
  }
  $scope.createBuyer = function(user) {
    console.log("NOTHIGN IS HAPPENING?");

    HomeService.createBuyer(user)
      .then(function(data) {
        console.log('SUCCESS: ', data);
        $location.path('/buyers');
      },function error(err) {
        console.log("FUCK!: ", err);
      })
  }
}
