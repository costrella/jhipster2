(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WeekController', WeekController);

    WeekController.$inject = ['$filter', '$scope', '$cookies', '$state', 'Week', 'Person', 'WeekSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function WeekController ($filter, $scope, $cookies, $state, Week, Person, WeekSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
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

        vm.fromDate = null;
        vm.toDate = null;
        vm.today = today;
        vm.previousMonth = previousMonth;
        vm.previousMonth();
        vm.people = Person.query();
        vm.ph = $cookies.getObject('week_ph');
        vm.page = 1;

        loadAll();

        function getPersonId() {
			$cookies.putObject('week_ph', vm.ph);
            if (vm.ph) {
                return vm.ph.id;
            }
            return null;
        }

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);
			
			$cookies.put('week_fromDate', vm.fromDate);
			$cookies.put('week_toDate', vm.toDate);

                Week.query({
                    page: vm.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    fromDate: fromDate,
                    toDate: toDate,
                    personId: getPersonId(),
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
                vm.weeks = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.loadAll();
        }

        function today () {
            var today = new Date();
            vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        }

        function previousMonth () {
            var fromDate = new Date();
            var toDate = new Date();
            toDate.setMonth(toDate.getMonth()+1);
            //vm.fromDate = fromDate;
            //vm.toDate = toDate;
			
			if($cookies.get('week_fromDate')){
				vm.fromDate = new Date($cookies.get('week_fromDate'));
			}else{
				vm.fromDate = fromDate;
			}
			
			if($cookies.get('week_toDate')){
				vm.toDate = new Date($cookies.get('week_toDate'));
			}else{
				vm.toDate = toDate;
			}
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
