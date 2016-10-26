(function() {
    'use strict';
    angular
        .module('cechiniApp')
        .factory('Store', Store);

    Store.$inject = ['$resource'];

    function Store ($resource) {
        var resourceUrl =  'api/stores/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'queryCount': { method: 'GET', isArray: false, params: {storeId: null}},
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
