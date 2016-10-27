(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Store', 'Raport', 'Week', 'ParseLinks', 'paginationConstants'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Store, Raport, Week, ParseLinks, paginationConstants) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;
        vm.loadAll = loadAll;
        vm.getTarget = getTarget;

        var unsubscribe = $rootScope.$on('cechiniApp:personUpdate', function (event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();
        vm.getTarget();

        function loadAll() {

            Raport.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
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
            }

            function onError(error) {
                // AlertService.error(error.data.message);
                console.log(error.data.message);
            }
        }
    }
})();
