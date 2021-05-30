angular.module('app')
    .controller('addEventController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.addEvent = function () {
            console.log('addEvent $http.post '+ API_ENDPOINT + '/api/v1/event/, $scope.event = '+$scope.event.data)
            $http.post(API_ENDPOINT + '/api/v1/event/', $scope.event)
                .then(function (response) {
                    console.log(response.data)
                    $scope.newEvent = null;
                    window.alert("Event добавлен!");
                });
        };
});


