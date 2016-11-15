(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WarehouseDeleteController',WarehouseDeleteController);

    WarehouseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Warehouse'];

    function WarehouseDeleteController($uibModalInstance, entity, Warehouse) {
        var vm = this;

        vm.warehouse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Warehouse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
