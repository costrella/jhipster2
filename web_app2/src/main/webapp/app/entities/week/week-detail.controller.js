(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WeekDetailController', WeekDetailController);

    WeekDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Week', 'Day', 'Person', 'ParseLinks', 'paginationConstants'];

    function WeekDetailController($scope, $rootScope, $stateParams, previousState, entity, Week, Day, Person, ParseLinks, paginationConstants) {
        var vm = this;

        vm.week = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.loadPage = loadPage;
        vm.loadAll = loadAll;

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

        function transition() {
            vm.loadAll();
        }
    }
})();
