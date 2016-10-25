(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WeekDetailController', WeekDetailController);

    WeekDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Week', 'Day', 'Raport', 'Person', 'ParseLinks', 'paginationConstants'];

    function WeekDetailController($scope, $rootScope, $stateParams, previousState, entity, Week, Day, Raport, Person, ParseLinks, paginationConstants) {
        var vm = this;

        vm.week = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.loadPage = loadPage;
        vm.loadPage2 = loadPage2;
        vm.loadAll = loadAll;
        vm.loadAll2 = loadAll2;

        var unsubscribe = $rootScope.$on('cechiniApp:weekUpdate', function (event, result) {
            vm.week = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();

        function loadAll() {

            Day.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
                weekId: vm.week.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.days = data;
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

        vm.loadAll2();

        function loadAll2() {

            Raport.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
                weekId: vm.week.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems2 = headers('X-Total-Count');
                vm.queryCount = vm.totalItems2;
                vm.raports = data;
            }

            function onError(error) {
                // AlertService.error(error.data.message);
                console.log(error.data.message);
            }
        }

        function loadPage2(page) {
            vm.page = page;
            vm.loadAll2()
        }

        function transition() {
            vm.loadAll();
        }
    }
})();
