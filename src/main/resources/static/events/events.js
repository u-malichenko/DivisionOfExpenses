angular.module('app')
    .controller('eventsController', function (API_ENDPOINT, sharedEventId, $scope, $http, $location) {

    $scope.showMyEvents = function () {
        $http({
            url: API_ENDPOINT + '/api/v1/event',
            method: 'GET'
        }).then(function (response) {
            $scope.MyEvents = response.data;
        });
    };

    $scope.goToEventId = function (eventId) {
        sharedEventId.setEventId(eventId);
        $location.path('/event');
    }

    $scope.showMyEvents();
});
