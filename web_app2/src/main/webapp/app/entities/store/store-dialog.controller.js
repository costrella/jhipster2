(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoreDialogController', StoreDialogController);

    StoreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Store', 'Person', 'Raport', 'Day', 'Storegroup', 'Address'];

    function StoreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Store, Person, Raport, Day, Storegroup, Address) {
        var vm = this;

        vm.store = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.people = Person.query();
        vm.raports = Raport.query();
        vm.days = Day.query();
        vm.storegroups = Storegroup.query();
        vm.addresses = Address.queryAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.store.id !== null) {
                Store.update(vm.store, onSaveSuccess, onSaveError);
            } else {
                Store.save(vm.store, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cechiniApp:storeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPicture01 = function ($file, store) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        store.picture01 = base64Data;
                        store.picture01ContentType = $file.type;
                    });
                });
            }
        };

        vm.setPicture02 = function ($file, store) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        store.picture02 = base64Data;
                        store.picture02ContentType = $file.type;
                    });
                });
            }
        };

    }
})();
