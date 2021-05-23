angular.module('app').controller('eventsController', function (API_ENDPOINT, $scope, $http) {

    $scope.showMyEvents = function () {
        $http({
            url: API_ENDPOINT + '/api/v1/event/dto',
            method: 'GET'
        }).then(function (response) {
            $scope.MyEvents = response.data;
        });
    };

    $scope.editEvent = function (eventId) {
        $http({
            url: API_ENDPOINT + '/api/v1/event/' + eventId,
            method: 'GET'
        }).then(function (response) {
            console.log("open event id "+eventId)
        });
    };

    $scope.showMyEvents();


});