angular.module('app')
    .controller('homeController', function (API_ENDPOINT, sharedParam, $scope, $http, $localStorage) {
    console.log(API_ENDPOINT);
    console.log(sharedParam.getEventId());
    console.log(sharedParam.getExpenseId());
});
