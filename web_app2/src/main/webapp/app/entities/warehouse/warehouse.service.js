(function() {
    'use strict';
    angular
        .module('cechiniApp')
        .factory('Warehouse', Warehouse);

    Warehouse.$inject = ['$resource'];

    function Warehouse ($resource) {
        var resourceUrl =  'api/warehouses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
