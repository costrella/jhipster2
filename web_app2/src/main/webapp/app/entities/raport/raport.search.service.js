(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .factory('RaportSearch', RaportSearch);

    RaportSearch.$inject = ['$resource'];

    function RaportSearch($resource) {
        var resourceUrl =  'api/_search/raports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
