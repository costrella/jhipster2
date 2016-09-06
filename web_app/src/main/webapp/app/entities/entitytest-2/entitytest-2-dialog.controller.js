(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest2DialogController', Entitytest2DialogController);

    Entitytest2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Entitytest2'];

    function Entitytest2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Entitytest2) {
        var vm = this;

        vm.entitytest2 = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entitytest2.id !== null) {
                Entitytest2.update(vm.entitytest2, onSaveSuccess, onSaveError);
            } else {
                Entitytest2.save(vm.entitytest2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:entitytest2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setTest1 = function ($file, entitytest2) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        entitytest2.test1 = base64Data;
                        entitytest2.test1ContentType = $file.type;
                    });
                });
            }
        };

    }
})();
