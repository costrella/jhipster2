(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoregroupDialogController', StoregroupDialogController);

    StoregroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Storegroup', 'Store'];

    function StoregroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Storegroup, Store) {
        var vm = this;

        vm.storegroup = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stores = Store.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.storegroup.id !== null) {
                Storegroup.update(vm.storegroup, onSaveSuccess, onSaveError);
            } else {
                Storegroup.save(vm.storegroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cechiniApp:storegroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
