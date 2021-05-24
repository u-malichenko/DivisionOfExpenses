angular.module('app')
    .controller('expensesController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.showExpenses = function () {
            $http({
                url: API_ENDPOINT + '/api/v1/expense/event/' + sharedParam.getEventId,
                method: 'GET'
            }).then(function (response) {
                $scope.expenses = response.data;
            });
        };

        $scope.goToExpenseId = function (expenseId) {
            sharedParam.setExpenseId(expenseId);
            $location.path('/expense');
        }

        $scope.addExpense = function () {
            $location.path('/addexpense');
        }
        $scope.showExpenses();
    });
