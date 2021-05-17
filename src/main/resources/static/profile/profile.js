// angular.module('app').controller('profileController', function ($scope, $http) { //, jwtHelper
//     const contextPath = 'http://localhost:8189/market';
//
//     $scope.updateProfile = function () {
//          $http.put(contextPath + '/api/v1/profile', $scope.profile)
//             .then(function () {
//                 $scope.loadProfile();
//                 window.alert("Профиль обновлен");
//             });
//     };
//
//     $scope.loadProfile = function () {
//         $http.get(contextPath + '/api/v1/profile')
//         .then(function (response) {
//             $scope.profile = response.data;
//         });
//     };
//
//     // $scope.getUserRole = function() {
//     //     let tokenPayload = jwtHelper.decodeToken($localStorage.currentUser.token);
//     //     $scope.userRoles = tokenPayload.roles;
//     // };
//
//     $scope.loadProfile();
// });