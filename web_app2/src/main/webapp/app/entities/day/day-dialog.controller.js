(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('DayDialogController', DayDialogController);

    DayDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Day', 'Week', 'Store'];

    function DayDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Day, Week, Store) {
        var vm = this;

        vm.day = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.weeks = Week.query();
        vm.stores = Store.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.day.id !== null) {
                Day.update(vm.day, onSaveSuccess, onSaveError);
            } else {
                Day.save(vm.day, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cechiniApp:dayUpdate', result);
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
