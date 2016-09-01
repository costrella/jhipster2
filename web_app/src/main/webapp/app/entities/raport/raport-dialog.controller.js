(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('RaportDialogController', RaportDialogController);

    RaportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Raport', 'Person', 'Store'];

    function RaportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Raport, Person, Store) {
        var vm = this;

        vm.raport = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query();
        vm.stores = Store.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.raport.id !== null) {
                Raport.update(vm.raport, onSaveSuccess, onSaveError);
            } else {
                Raport.save(vm.raport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:raportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
