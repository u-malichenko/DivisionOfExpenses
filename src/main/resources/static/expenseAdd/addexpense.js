angular.module('app')
    .controller('addExpenseController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.addExpense = function () {
            $http.post(API_ENDPOINT + '/api/v1/expense/', $scope.newExpense)
                .then(function (response) {
                    $scope.newExpense = null;
                    console.log(response.data());
                    window.alert("Expense добавлен!");
                });
        };
});

