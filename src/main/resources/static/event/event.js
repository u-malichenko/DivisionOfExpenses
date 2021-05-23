angular.module('app').controller('eventsController', function (API_ENDPOINT, sharedEventId, $scope, $http, $location) {

    $scope.loadEvent = function () {
        $http({
            url: API_ENDPOINT + '/api/v1/event/' + sharedEventId.getEventId,
            method: 'GET'
        }).then(function (response) {
            $scope.event = response.data;
        });
    };

    $scope.updateEvent = function () {
        $http.put(API_ENDPOINT + '/api/v1/event/', $scope.event)
            .then(function () {
                $scope.loadEvent();
                window.alert("Event обновлен");
            });
    };

    $scope.loadEvent();
});


