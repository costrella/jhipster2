(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoreController', StoreController);

    StoreController.$inject = ['$scope', '$state', '$cookies', 'DataUtils', 'Store', 'Storegroup', 'StoreSearch', 'Person', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function StoreController ($scope, $state, $cookies, DataUtils, Store, Storegroup, StoreSearch, Person, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.people = Person.query();
        vm.storegroups = Storegroup.queryAll();
		vm.ph = $cookies.getObject('store_ph');
		vm.storegroup = $cookies.getObject('store_storegroup');
        vm.page = 1;

        loadAll();

        function getPersonId() {
			$cookies.putObject('store_ph', vm.ph);
            if (vm.ph) {
                return vm.ph.id;
            }
            return null;
        }
		
		function getStoregroupId() {
			$cookies.putObject('store_storegroup', vm.storegroup);
            if (vm.storegroup) {
                return vm.storegroup.id;
            }
            return null;
        }

        function loadAll () {
                Store.query({
                    page: vm.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    personId: getPersonId(),
					storegroupId: getStoregroupId()
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
                vm.stores = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.loadAll()
        }

        function transition () {
            vm.loadAll();
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear () {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }
    }
})();
