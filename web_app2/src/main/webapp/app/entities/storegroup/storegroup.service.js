(function() {
    'use strict';
    angular
        .module('cechiniApp')
        .factory('Storegroup', Storegroup);

    Storegroup.$inject = ['$resource'];

    function Storegroup ($resource) {
        var resourceUrl =  'api/storegroups/:id';

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
