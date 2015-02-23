'use strict';

/**
 * @ngdoc function
 * # MainCtrl
 */
angular.module('BootstrapApplication.controllers')
        .controller('MainCtrl', ['$scope', 'ClientService', function ($scope, ClientService) {
            $scope.awesomeThings = [
                'HTML5 Boilerplate',
                'AngularJS',
                'Karma'
            ];

            $scope.client = {
                firstName: '',
                lastName: '',
                age: '',
                amount: '',
                jobPosition: ''
            };

            $scope.alerts = [];

            $scope.appResult = '';

            $scope.applyForLoan = function() {
              ClientService
                    .applyForLoan($scope.client)
                    .success(function (data) {
                        $scope.appResult = data;
                    })
                    .error(function(reason) {
                        $scope.alerts = [{msg: reason}];
                    });
            };

            $scope.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };
        }]);
