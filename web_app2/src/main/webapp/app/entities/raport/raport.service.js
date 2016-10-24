(function() {
    'use strict';
    angular
        .module('cechiniApp')
        .factory('Raport', Raport);

    Raport.$inject = ['$resource', 'DateUtils'];

    function Raport ($resource, DateUtils) {
        var resourceUrl =  'api/raports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {fromDate: null, toDate: null}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertLocalDateFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
