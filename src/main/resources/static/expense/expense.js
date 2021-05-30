angular.module('app')
    .controller('expenseController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.loadExpense = function () {
            console.log('GET '+API_ENDPOINT + '/api/v1/expense/' + sharedParam.getExpenseId())
            $http({
                url: API_ENDPOINT + '/api/v1/expense/' + sharedParam.getExpenseId(),
                method: 'GET'
            }).then(function (response) {
                console.log(response.data)
                $scope.event = response.data;
            });
        };

        $scope.updateExpense = function () {
            console.log('updateExpense $http.put '+ API_ENDPOINT + '/api/v1/expense/, $scope.expense = '+$scope.expense.data)
            $http.put(API_ENDPOINT + '/api/v1/expense/', $scope.expense)
                .then(function () {
                    $scope.loadEvent();
                    window.alert("Expense обновлен");
                });
        };

        $scope.loadExpense();
});

