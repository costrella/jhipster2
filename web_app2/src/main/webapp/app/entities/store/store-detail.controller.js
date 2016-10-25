(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoreDetailController', StoreDetailController);

    StoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Store', 'Person', 'Raport', 'Day', 'ParseLinks', 'paginationConstants'];

    function StoreDetailController($scope, $rootScope, $stateParams, previousState, entity, Store, Person, Raport, Day, ParseLinks, paginationConstants) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.loadPage = loadPage;

        vm.loadAll = loadAll;

        var unsubscribe = $rootScope.$on('cechiniApp:storeUpdate', function (event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();


        function loadAll() {

            Raport.query({
                page: vm.page - 1,
                size: vm.itemsPerPage,
                storeId: vm.store.id
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
