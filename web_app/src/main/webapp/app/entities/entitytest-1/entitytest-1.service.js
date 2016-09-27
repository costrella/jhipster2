(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Entitytest1', Entitytest1);

    Entitytest1.$inject = ['$resource', 'DateUtils'];

    function Entitytest1 ($resource, DateUtils) {
        var resourceUrl =  'api/entitytest-1-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data1 = DateUtils.convertLocalDateFromServer(data.data1);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.data1 = DateUtils.convertLocalDateToServer(data.data1);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.data1 = DateUtils.convertLocalDateToServer(data.data1);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
