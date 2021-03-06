(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportController', RaportController);

    RaportController.$inject = ['$filter', '$scope', '$cookies', '$state', 'DataUtils', 'Raport', 'RaportSearch', 'Person', 'Warehouse', 'Store', 'Storegroup', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function RaportController ($filter , $scope, $cookies, $state, DataUtils, Raport, RaportSearch, Person, Warehouse, Store, Storegroup, ParseLinks, AlertService, pagingParams, paginationConstants) {
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
        
        vm.today = today;
        vm.previousMonth = previousMonth;
        vm.previousMonth();
        vm.people = Person.query();
        vm.stores = Store.queryAll();
        vm.warehouses = Warehouse.queryAll();
        vm.storegroups = Storegroup.queryAll();
        
        vm.page = 1;
		vm.ph = $cookies.getObject('ph');
		vm.store = $cookies.getObject('store');
		vm.warehouse = $cookies.getObject('warehouse');
		vm.storegroup = $cookies.getObject('storegroup');
		
		vm.raport_id = $cookies.get('raport_id');
		
		
		
		vm.toDate1 =  new Date($cookies.get('toDate1'));

        vm.loadAll();
		
        function getStoreId() {
			$cookies.putObject('store', vm.store);
            if (vm.store) {
                return vm.store.id;
            }
            return null;
        }

        function getPersonId() {
			$cookies.putObject('ph', vm.ph);
            if (vm.ph) {
                return vm.ph.id;
            }
            return -1;
        }

        function getWarehouseId() {
			$cookies.putObject('warehouse', vm.warehouse);
            if (vm.warehouse) {
                return vm.warehouse.id;
            }
            return null;
        }

        function getStoregroupId() {
			$cookies.putObject('storegroup', vm.storegroup);
            if (vm.storegroup) {
                return vm.storegroup.id;
            }
            return null;
        }

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);
			$cookies.put('fromDate', vm.fromDate);
			$cookies.put('toDate', vm.toDate);


            Raport.query({
                    page: vm.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    fromDate: fromDate,
                    toDate: toDate,
                    person: getPersonId(),
                    storeId: getStoreId(),
                    warehouseId: getWarehouseId(),
                    storegroupId: getStoregroupId()
            }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'desc' : 'asc')];
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
                storeId: getStoreId(),
                warehouseId: getWarehouseId(),
                storegroupId: getStoregroupId()
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
			
            //var today = new Date();
            //vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
			//vm.toDate = new Date($cookies.get('toDate'));
			
        }

        function previousMonth () {
            var fromDate = new Date();
            var toDate = new Date();
            fromDate.setDate(fromDate.getDate()-1);
            //vm.fromDate = fromDate;
            //vm.toDate = toDate;
			
			if($cookies.get('fromDate')){
				vm.fromDate = new Date($cookies.get('fromDate'));
			}else{
				vm.fromDate = fromDate;
			}
			
			if($cookies.get('toDate')){
				vm.toDate = new Date($cookies.get('toDate'));
			}else{
				vm.toDate = toDate;
			}
        }


        function loadPage (page) {
            vm.page = page;
            vm.loadAll()
        }

        function transition () {
            vm.loadAll();
        }

		$scope.getImage = function(data){
			return 'data:image/jpeg;base64,' + data;
		}
    }
})();
