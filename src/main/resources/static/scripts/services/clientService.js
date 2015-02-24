'use strict';

angular.module('BootstrapApplication.services')
  .factory('ClientService', ['$http', function($http) {
    var clientService = {};
    clientService.applyForLoan = function (client) {
      return $http({
        url: '/client/apply',
        dataType: 'json',
        method: 'POST',
        data: client,
        headers: {
          'Content-Type': 'application/json'
        }
      });
    };

    return clientService;
  }
  ]);
