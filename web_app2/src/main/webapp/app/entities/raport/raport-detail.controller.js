(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportDetailController', RaportDetailController);

    RaportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Raport', 'Person', 'Store'];

    function RaportDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Raport, Person, Store) {
        var vm = this;

        vm.raport = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cechiniApp:raportUpdate', function(event, result) {
            vm.raport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
