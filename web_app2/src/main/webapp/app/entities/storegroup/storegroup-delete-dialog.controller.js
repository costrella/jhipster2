(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoregroupDeleteController',StoregroupDeleteController);

    StoregroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'Storegroup'];

    function StoregroupDeleteController($uibModalInstance, entity, Storegroup) {
        var vm = this;

        vm.storegroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Storegroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
