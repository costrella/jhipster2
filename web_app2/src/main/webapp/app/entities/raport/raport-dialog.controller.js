(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportDialogController', RaportDialogController);

    RaportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Raport', 'Person', 'Store'];

    function RaportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Raport, Person, Store) {
        var vm = this;

        vm.raport = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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
            $scope.$emit('cechiniApp:raportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto1 = function ($file, raport) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        raport.foto1 = base64Data;
                        raport.foto1ContentType = $file.type;
                    });
                });
            }
        };

        vm.setFoto2 = function ($file, raport) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        raport.foto2 = base64Data;
                        raport.foto2ContentType = $file.type;
                    });
                });
            }
        };

        vm.setFoto3 = function ($file, raport) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        raport.foto3 = base64Data;
                        raport.foto3ContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
