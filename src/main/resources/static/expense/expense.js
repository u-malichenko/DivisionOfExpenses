angular.module('app')
    .controller('expenseController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.loadExpense = function () {
            $http({
                url: API_ENDPOINT + '/api/v1/expense/' + sharedParam.getExpenseId(),
                method: 'GET'
            }).then(function (response) {
                $scope.event = response.data;
            });
        };

        $scope.updateExpense = function () {
            $http.put(API_ENDPOINT + '/api/v1/expense/', $scope.expense)
                .then(function () {
                    $scope.loadEvent();
                    window.alert("Expense обновлен");
                });
        };

        $scope.loadExpense();
});

