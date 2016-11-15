(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WarehouseDetailController', WarehouseDetailController);

    WarehouseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Warehouse', 'Raport'];

    function WarehouseDetailController($scope, $rootScope, $stateParams, previousState, entity, Warehouse, Raport) {
        var vm = this;

        vm.warehouse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cechiniApp:warehouseUpdate', function(event, result) {
            vm.warehouse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
