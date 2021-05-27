angular.module('app')
    .controller('expensesController', function (API_ENDPOINT, sharedParam, $scope, $http, $location) {

        $scope.showExpenses = function () {
            console.log(API_ENDPOINT + '/api/v1/expense/ByEventId/' + sharedParam.getEventId());
            $http({
                ///api/v1/expense//ByEventId/{id}
                url: API_ENDPOINT + '/api/v1/expense/ByEventId/' + sharedParam.getEventId(),
                method: 'GET'
            }).then(function (response) {
                console.log(response.data);
                $scope.expenses = response.data;
            });
        };

        $scope.goToExpenseId = function (expenseId) {
            sharedParam.setExpenseId(expenseId);
            console.log('goToExpenseId $location.path(\'/expense\'); expenseId ='+sharedParam.getExpenseId);
            $location.path('/expense');
        }

        $scope.addExpense = function () {
            console.log('addExpense $location.path(\'/addexpense\'');
            $location.path('/addexpense');
        }
        $scope.showExpenses();
    });
