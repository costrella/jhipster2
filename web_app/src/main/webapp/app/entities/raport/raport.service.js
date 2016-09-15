(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Raport', Raport);

    Raport.$inject = ['$resource'];

    function Raport ($resource) {
        var resourceUrl =  'api/raports/:id';

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
