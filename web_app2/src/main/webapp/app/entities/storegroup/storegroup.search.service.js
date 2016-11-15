(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .factory('StoregroupSearch', StoregroupSearch);

    StoregroupSearch.$inject = ['$resource'];

    function StoregroupSearch($resource) {
        var resourceUrl =  'api/_search/storegroups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
