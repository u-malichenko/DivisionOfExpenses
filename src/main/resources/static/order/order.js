// angular.module('app').controller('orderController', function ($scope, $http) {
//     const contextPath = 'http://localhost:8189/market';
//
//     $scope.createNewOrder = function () {
//         $http({
//             url: contextPath + '/api/v1/orders',
//             method: 'POST',
//             params: {
//                 receiverName: $scope.order.receiverName,
//                 phone: $scope.order.phone,
//                 address: $scope.order.address
//             }
//         })
//             .then(function (response) {
//                 alert('Заказ оформлен')
//                 $scope.order = null;
//                 $scope.newOrderContentRequest();
//             });
//     };
//
//     $scope.newOrderContentRequest = function () {
//         $http({
//             url: contextPath + '/api/v1/orders/newOrder',
//             method: 'GET'
//         }).then(function (response) {
//             console.log(response.data);
//             $scope.cart = response.data;
//         });
//     };
//
//     $scope.newOrderContentRequest();
// });