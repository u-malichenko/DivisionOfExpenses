angular.module('app')
    .controller('eventsController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.showMyEvents = function () {
            console.log('GET ' + API_ENDPOINT + '/api/v1/event')
            $http({
                url: API_ENDPOINT + '/api/v1/event',
                method: 'GET'
            }).then(function (response) {
                console.log(response.data)
                $scope.events = response.data;
            });
        };

        $scope.goToEventId = function (eventId) {
            sharedParam.setEventId(eventId);
            console.log('goToEventId $location.path(\'/event\'); eventId = ' + sharedParam.getEventId());
            $location.path('/event');
        }

        $scope.deleteEventId = function (eventId) {
            sharedParam.setEventId(eventId);
            console.log('deleteEventId $location.path(\'/event\'); eventId = ' + sharedParam.getEventId());
            $http({
                url: API_ENDPOINT + '/api/v1/event/' + eventId,
                method: 'DELETE'
            })
            $scope.showMyEvents();
        }

        $scope.addEvent = function () {
            console.log('addEvent $location.path(\'/addevent\'');
            $location.path('/addevent');
        }

        $scope.showMyEvents();
    });
