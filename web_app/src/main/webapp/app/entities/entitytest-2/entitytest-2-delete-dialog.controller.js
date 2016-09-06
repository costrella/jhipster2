(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest2DeleteController',Entitytest2DeleteController);

    Entitytest2DeleteController.$inject = ['$uibModalInstance', 'entity', 'Entitytest2'];

    function Entitytest2DeleteController($uibModalInstance, entity, Entitytest2) {
        var vm = this;

        vm.entitytest2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entitytest2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
