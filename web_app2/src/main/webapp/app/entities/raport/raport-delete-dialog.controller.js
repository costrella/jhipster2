(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportDeleteController',RaportDeleteController);

    RaportDeleteController.$inject = ['$uibModalInstance', 'entity', 'Raport'];

    function RaportDeleteController($uibModalInstance, entity, Raport) {
        var vm = this;

        vm.raport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Raport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
