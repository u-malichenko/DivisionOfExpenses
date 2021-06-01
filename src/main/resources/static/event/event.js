angular.module('app')
    .controller('eventController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

    $scope.loadEvent = function () {
        console.log('GET '+API_ENDPOINT + '/api/v1/event/' + sharedParam.getEventId())
        $http({
            url: API_ENDPOINT + '/api/v1/event/' + sharedParam.getEventId(),
            method: 'GET'
        }).then(function (response) {
            console.log(response.data);
            $scope.event = response.data;
        });
    };

    $scope.updateEvent = function () {
        console.log('updateEvent $http.put '+ API_ENDPOINT + '/api/v1/event/, $scope.event = '+$scope.event.data)
        $http.put(API_ENDPOINT + '/api/v1/event/', $scope.event)
            .then(function () {
                $scope.loadEvent();
                window.alert("Event обновлен");
            });
    };

    $scope.goToExpenses = function (eventId) {
        sharedParam.setEventId(eventId);
        console.log('goToExpenses $location.path(\'/expenses\'); eventId = ' + sharedParam.getEventId());
        $location.path('/expenses');
    }

    $scope.loadEvent();
});


