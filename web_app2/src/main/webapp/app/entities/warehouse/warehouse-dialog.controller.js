(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WarehouseDialogController', WarehouseDialogController);

    WarehouseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Warehouse', 'Raport'];

    function WarehouseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Warehouse, Raport) {
        var vm = this;

        vm.warehouse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.raports = Raport.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.warehouse.id !== null) {
                Warehouse.update(vm.warehouse, onSaveSuccess, onSaveError);
            } else {
                Warehouse.save(vm.warehouse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cechiniApp:warehouseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
