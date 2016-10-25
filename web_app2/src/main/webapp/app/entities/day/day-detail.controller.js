(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('DayDetailController', DayDetailController);

    DayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Day', 'Week', 'Store', 'Raport', 'ParseLinks', 'paginationConstants'];

    function DayDetailController($scope, $rootScope, $stateParams, previousState, entity, Day, Week, Store, Raport, ParseLinks, paginationConstants) {
        var vm = this;

        vm.day = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.loadPage = loadPage;

        vm.loadAll = loadAll;

        var unsubscribe = $rootScope.$on('cechiniApp:dayUpdate', function (event, result) {
            vm.day = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();

        function loadAll() {

            Raport.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
                personId: vm.day.week.person.id,
                dayId: vm.day.id
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

        function transition() {
            vm.loadAll();
        }
    }
})();
