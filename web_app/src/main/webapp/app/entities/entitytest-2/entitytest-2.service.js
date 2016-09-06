(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Entitytest2', Entitytest2);

    Entitytest2.$inject = ['$resource'];

    function Entitytest2 ($resource) {
        var resourceUrl =  'api/entitytest-2-s/:id';

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
