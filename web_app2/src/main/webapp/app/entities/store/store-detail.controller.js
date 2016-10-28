(function () {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoreDetailController', StoreDetailController);

    StoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DataUtils', 'Store', 'Person', 'Raport', 'Day', 'ParseLinks', 'paginationConstants'];

    function StoreDetailController($scope, $rootScope, $stateParams, previousState, entity, DataUtils, Store, Person, Raport, Day, ParseLinks, paginationConstants) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.loadPage = loadPage;
        vm.visitCount = visitCount;
        vm.loadAll = loadAll;

        var unsubscribe = $rootScope.$on('cechiniApp:storeUpdate', function (event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.loadAll();
        vm.visitCount();


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

        function visitCount() {
            Store.queryCount({
                storeId: vm.store.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.month = data.month;
                vm.monthAgo = data.monthAgo;
                vm.allCount = data.allCount;
            }

            function onError(error) {
                // AlertService.error(error.data.message);
                console.log(error.data.message);
            }
        }

    }
})();
