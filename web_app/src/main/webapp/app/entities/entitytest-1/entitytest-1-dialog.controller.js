(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest1DialogController', Entitytest1DialogController);

    Entitytest1DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entitytest1'];

    function Entitytest1DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entitytest1) {
        var vm = this;

        vm.entitytest1 = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entitytest1.id !== null) {
                Entitytest1.update(vm.entitytest1, onSaveSuccess, onSaveError);
            } else {
                Entitytest1.save(vm.entitytest1, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:entitytest1Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
