angular.module('app')
    .controller('eventController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

    $scope.loadEvent = function () {
        $http({
            url: API_ENDPOINT + '/api/v1/event/' + sharedParam.getEventId,
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

    $scope.goToExpenses = function (eventId) {
        sharedParam.setEventId(eventId);
        $location.path('/expenses');
    }

    $scope.loadEvent();
});


