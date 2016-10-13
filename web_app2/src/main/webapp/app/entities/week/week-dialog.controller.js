(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WeekDialogController', WeekDialogController);

    WeekDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Week', 'Day', 'Person'];

    function WeekDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Week, Day, Person) {
        var vm = this;

        vm.week = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.days = Day.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.week.id !== null) {
                Week.update(vm.week, onSaveSuccess, onSaveError);
            } else {
                Week.save(vm.week, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cechiniApp:weekUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateBefore = false;
        vm.datePickerOpenStatus.dateAfter = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
