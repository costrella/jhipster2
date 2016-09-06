(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest1DeleteController',Entitytest1DeleteController);

    Entitytest1DeleteController.$inject = ['$uibModalInstance', 'entity', 'Entitytest1'];

    function Entitytest1DeleteController($uibModalInstance, entity, Entitytest1) {
        var vm = this;

        vm.entitytest1 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entitytest1.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
