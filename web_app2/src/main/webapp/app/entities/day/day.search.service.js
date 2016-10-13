(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .factory('DaySearch', DaySearch);

    DaySearch.$inject = ['$resource'];

    function DaySearch($resource) {
        var resourceUrl =  'api/_search/days/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
