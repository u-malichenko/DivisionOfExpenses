angular.module('app').controller('eventsController', function ($scope, $http) {

    $scope.showMyEvents = function () {
        $http({
            url: $scope.API_ENDPOINT + '/api/v1/event/dto',
            method: 'GET'
        }).then(function (response) {
            $scope.MyEvents = response.data;
        });
    };

    $scope.showMyEvents();
});