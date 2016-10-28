(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$filter', '$scope', '$rootScope', '$stateParams', 'DataUtils', 'previousState', 'entity', 'Person', 'Store', 'Raport', 'Week', 'ParseLinks', 'paginationConstants'];

    function PersonDetailController($filter, $scope, $rootScope, $stateParams, DataUtils, previousState, entity, Person, Store, Raport, Week, ParseLinks, paginationConstants) {
        var vm = this;

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.person = entity;
        vm.previousState = previousState.name;
        vm.loadAll = loadAll;
        vm.getTarget = getTarget;
        vm.fromDate = null;
        vm.toDate = null;
        vm.today = today;
        vm.previousDay = previousDay;
        vm.previousDay();

        var unsubscribe = $rootScope.$on('cechiniApp:personUpdate', function (event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();
        vm.getTarget();

        function loadAll() {

            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);

            Raport.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
                fromDate: fromDate,
                toDate: toDate,
                person: vm.person.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.raports = data;
            }

            function onError(error) {
                // AlertService.error(error.data.message);
                console.log(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.loadAll()
        }

        function getTarget() {
            Person.queryTarget({
                personId: vm.person.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.targetMain = data.targetMain;
                vm.sumAll = data.sumAll;
                vm.sumAllPercent = data.sumAllPercent;
                vm.target01 = data.target01;
                vm.target02 = data.target02;
                vm.target03 = data.target03;
                vm.target04 = data.target04;
                vm.target05 = data.target05;
                vm.target06 = data.target06;
                vm.target07 = data.target07;
                vm.target08 = data.target08;
            }

            function onError(error) {
                // AlertService.error(error.data.message);
                console.log(error.data.message);
            }
        }

        function today () {
            var today = new Date();
            vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        }

        function previousDay () {
            var fromDate = new Date();
            var toDate = new Date();
            // fromDate.setDate(fromDate.getDate()-1);
            fromDate.setMonth(fromDate.getMonth()-1);
            vm.fromDate = fromDate;
            vm.toDate = toDate;
        }
    }
})();
