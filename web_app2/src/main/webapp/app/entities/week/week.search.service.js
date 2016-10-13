(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .factory('WeekSearch', WeekSearch);

    WeekSearch.$inject = ['$resource'];

    function WeekSearch($resource) {
        var resourceUrl =  'api/_search/weeks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
