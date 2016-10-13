(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('WeekDeleteController',WeekDeleteController);

    WeekDeleteController.$inject = ['$uibModalInstance', 'entity', 'Week'];

    function WeekDeleteController($uibModalInstance, entity, Week) {
        var vm = this;

        vm.week = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Week.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
