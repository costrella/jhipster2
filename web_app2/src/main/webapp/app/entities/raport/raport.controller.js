(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportController', RaportController);

    RaportController.$inject = ['$scope', '$state', 'DataUtils', 'Raport', 'RaportSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function RaportController ($scope, $state, DataUtils, Raport, RaportSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
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

        loadAll();

        function loadAll () {
            if (pagingParams.search) {
                RaportSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Raport.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            }
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
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
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
		
		$scope.getTotalZ_a = function(){
		var total = 0;
		 for(var i = 0; i < $scope.vm.raports.length; i++){
			var raport = $scope.vm.raports[i];
			total += raport.z_a;
		}
		return total;
		}
		$scope.getTotalZ_b = function(){
		var total = 0;
		 for(var i = 0; i < $scope.vm.raports.length; i++){
			var raport = $scope.vm.raports[i];
			total += raport.z_b;
		}
		return total;
		}
		$scope.getTotalZ_c = function(){
		var total = 0;
		 for(var i = 0; i < $scope.vm.raports.length; i++){
			var raport = $scope.vm.raports[i];
			total += raport.z_c;
		}
		return total;
		}
		$scope.getTotalZ_d = function(){
		var total = 0;
		 for(var i = 0; i < $scope.vm.raports.length; i++){
			var raport = $scope.vm.raports[i];
			total += raport.z_d;
		}
		return total;
		}
		$scope.getTotalZ_e = function(){
		var total = 0;
		 for(var i = 0; i < $scope.vm.raports.length; i++){
			var raport = $scope.vm.raports[i];
			total += raport.z_e;
		}
		return total;
		}
		
		$scope.getImage = function(data){
			return 'data:image/jpeg;base64,' + data;
		}
    }
})();
