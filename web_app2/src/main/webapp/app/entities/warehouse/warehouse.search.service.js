(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .factory('WarehouseSearch', WarehouseSearch);

    WarehouseSearch.$inject = ['$resource'];

    function WarehouseSearch($resource) {
        var resourceUrl =  'api/_search/warehouses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
