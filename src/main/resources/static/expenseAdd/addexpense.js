angular.module('app')
    .controller('addExpenseController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.addExpense = function () {
            console.log(' $http.post '+API_ENDPOINT + '/api/v1/expense/ $scope.newExpense = ', $scope.expense.data)
            $http.post(API_ENDPOINT + '/api/v1/expense/addByEventId/'+sharedParam.getEventId(), $scope.expense)
                .then(function (response) {
                    console.log(response.data)
                    $scope.expense = null;
                    console.log(response.data());
                    window.alert("Expense добавлен!");
                });
        };
});

