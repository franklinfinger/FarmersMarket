angular
.module("farmers-profile.module")
.controller("FarmersProfileController", FarmersProfileController);

FarmersProfileController.$inject = ["$scope", "$http", "FarmersProfileService", "AuthService", "$uibModal"]

function FarmersProfileController($scope, $http, FarmersProfileService, AuthService, $uibModal){
  $scope.user = AuthService.currentUser();
  console.log("CHOKE RICHARD",AuthService.currentUser());

  $scope.editUser = function() {
    console.log("WHAT UP");
    var modalInstance = $uibModal.open({
      animation: $scope.animationsEnabled,
      templateUrl: './farmers-profile/views/modaledit.html',
      controller: 'ModalInstanceEditFarmerController',
      size: 'sm',
      resolve: {

      }
    });
  }
    FarmersProfileService.getAllInventoryByUser($scope.user.id)
    .then(function(data){
      $scope.myProducts = data.data;

    })


}
