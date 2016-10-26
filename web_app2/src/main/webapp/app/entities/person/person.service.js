(function() {
    'use strict';
    angular
        .module('cechiniApp')
        .factory('Person', Person);

    Person.$inject = ['$resource'];

    function Person ($resource) {
        var resourceUrl =  'api/people/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'queryTarget': { method: 'GET', isArray: false, params: {storeId: null}},
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
