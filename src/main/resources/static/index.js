(function ($localStorage) {
    'use strict';

    angular.module('app', ['ngRoute', 'ngStorage'])
        .constant('API_ENDPOINT', 'http://localhost:8189/')
        .config(config)
        .service('sharedParam', share)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/events', {
                templateUrl: 'events/events.html',
                controller: 'eventsController'
            })
            .when('/event', {
                templateUrl: 'event/event.html',
                controller: 'eventController'
            })
            .when('/addevent', {
                templateUrl: 'eventAdd/addevent.html',
                controller: 'addEventController'
            })
            .when('/expenses', {
                templateUrl: 'expenses/expenses.html',
                controller: 'expensesController'
            })
            .when('/expense', {
                templateUrl: 'expense/expense.html',
                controller: 'expenseController'
            })
            .when('/addexpense', {
                templateUrl: 'expenseAdd/addexpense.html',
                controller: 'addExpenseController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function share() {
        let eventId = null;
        let expenseId = null;
        return {
            getEventId: function () {
                return eventId;
            },
            setEventId: function(value) {
                eventId = value;
            },
            getExpenseId: function () {
                return expenseId;
            },
            setExpenseId: function(value) {
                expenseId = value;
            }
        };
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();

// angular.module('app', [])
//     .service('sharedEventId', function () {
//         let eventId = null;
//
//         return {
//             getEventId: function () {
//                 return eventId;
//             },
//             setEventId: function(value) {
//                 eventId = value;
//             }
//         };
//     });


angular.module('app')
    .controller('indexController', function (API_ENDPOINT, $scope, $http, $localStorage, $location) {
    $scope.tryToAuth = function () {
        $http.post(API_ENDPOINT + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.currentUserName = $scope.user.username;

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();

        $location.path('/');
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };
});