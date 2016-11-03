(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportController', RaportController);

    RaportController.$inject = ['$filter', '$scope', '$state', 'DataUtils', 'Raport', 'RaportSearch', 'Person', 'Store', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function RaportController ($filter ,$scope, $state, DataUtils, Raport, RaportSearch, Person, Store, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        // vm.clear = clear;
        // vm.search = search;
        vm.loadAll = loadAll;
        vm.raportCount = raportCount;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
		vm.searchQueryPerson = null;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.fromDate = null;
        vm.toDate = null;
        vm.today = today;
        vm.previousMonth = previousMonth;
        vm.previousMonth();
        vm.people = Person.query();
        vm.stores = Store.query();
        vm.ph = null;
        vm.page = 1;

        vm.loadAll();

        function getStoreId() {
            if (vm.store) {
                return vm.store.id;
            }
            return null;
        }

        function getPersonId() {
            if (vm.ph) {
                return vm.ph.id;
            }
            return -1;
        }

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);


            Raport.query({
                    page: vm.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    fromDate: fromDate,
                    toDate: toDate,
                    person: getPersonId(),
                    storeId: getStoreId()
            }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.raports = data;
                vm.raportCount();
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function raportCount () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);


            Raport.queryCount({
                test: 123,
                fromDate: fromDate,
                toDate: toDate,
                person: getPersonId(),
                storeId: getStoreId()
            }, onSuccess2, onError2);

            function onSuccess2(data, headers) {
                vm.queryCount = vm.totalItems;
                vm.a = data.a;
                vm.b = data.b;
                vm.c = data.c;
                vm.d = data.d;
                vm.e = data.e;
                vm.f = data.f;
                vm.g = data.g;
                vm.h = data.h;
            }
            function onError2(error) {
                AlertService.error(error.data.message);
            }
        }
        // Date picker configuration
        function today () {
            // Today + 1 day - needed if the current day must be included
            var today = new Date();
            vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        }

        function previousMonth () {
            var fromDate = new Date();
            var toDate = new Date();
            // if (fromDate.getMonth() === 0) {
            //     fromDate = new Date(fromDate.getFullYear() - 1, 11, fromDate.getDate());
            // } else {
            //     fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
            // }
            // fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth(), fromDate.getDate()-1);
            fromDate.setDate(fromDate.getDate()-1);
            vm.fromDate = fromDate;
            vm.toDate = toDate;
        }


        function loadPage (page) {
            vm.page = page;
            vm.loadAll()
        }

        function transition () {
            vm.loadAll();
        }

        // function search (searchQuery, searchQueryPerson) {
        //     if (!searchQueryPerson){
        //         return vm.clear();
        //     }
        //     vm.links = null;
        //     vm.page = 1;
        //     vm.predicate = '_score';
        //     vm.reverse = false;
			// //vm.currentSearch = searchQuery;
			// if (searchQueryPerson){
        //         vm.currentSearch = 'store.person.id: ' + searchQueryPerson;
        //     }
        //
        //     vm.transition();
        // }

        // function clear () {
        //     vm.links = null;
        //     vm.page = 1;
        //     vm.predicate = 'id';
        //     vm.reverse = true;
        //     vm.currentSearch = null;
        //     vm.transition();
        // }




		$scope.getImage = function(data){
			return 'data:image/jpeg;base64,' + data;
		}
    }
})();
