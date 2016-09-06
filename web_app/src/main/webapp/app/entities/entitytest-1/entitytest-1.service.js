(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Entitytest1', Entitytest1);

    Entitytest1.$inject = ['$resource'];

    function Entitytest1 ($resource) {
        var resourceUrl =  'api/entitytest-1-s/:id';

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
