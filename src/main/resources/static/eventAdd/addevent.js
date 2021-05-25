angular.module('app')
    .controller('addEventController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.addEvent = function () {
            $http.post(API_ENDPOINT + '/api/v1/event/', $scope.newEvent)
                .then(function (response) {
                    $scope.newEvent = null;
                    console.log(response.data());
                    window.alert("Event добавлен!");
                });
        };
});


