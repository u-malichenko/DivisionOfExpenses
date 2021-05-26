angular.module('app').controller('authController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/doe';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    console.log($localStorage.currentUser);
                }
            }, function errorCallback(response) {
                window.alert(response.data.message);
                $scope.clearUser();
            });
    };

    $scope.registerNewUser = function () {
        $http({
            url: contextPath + '/register',
            method: 'POST',
            params: {
                password: $scope.newUser.password,
                username: $scope.newUser.username,
                email: $scope.newProfile.email
            }
        })
            .then(function (response){
                // console.log(response.data());
                window.alert("New user register ok");
                $scope.newUser = null;
                $scope.newProfile = null;
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
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