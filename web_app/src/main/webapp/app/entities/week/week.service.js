(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Week', Week);

    Week.$inject = ['$resource', 'DateUtils'];

    function Week ($resource, DateUtils) {
        var resourceUrl =  'api/weeks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateBefore = DateUtils.convertLocalDateFromServer(data.dateBefore);
                        data.dateAfter = DateUtils.convertLocalDateFromServer(data.dateAfter);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateBefore = DateUtils.convertLocalDateToServer(data.dateBefore);
                    data.dateAfter = DateUtils.convertLocalDateToServer(data.dateAfter);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateBefore = DateUtils.convertLocalDateToServer(data.dateBefore);
                    data.dateAfter = DateUtils.convertLocalDateToServer(data.dateAfter);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
