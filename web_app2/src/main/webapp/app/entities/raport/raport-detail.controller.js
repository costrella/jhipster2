(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('RaportDetailController', RaportDetailController);

    RaportDetailController.$inject = ['$scope', '$rootScope', '$cookies', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Raport', 'Person', 'Store', 'Day', 'Warehouse'];

    function RaportDetailController($scope, $rootScope, $cookies, $stateParams, previousState, DataUtils, entity, Raport, Person, Store, Day, Warehouse) {
        var vm = this;

        vm.raport = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
		$cookies.put('raport_id', vm.raport.id);

        var unsubscribe = $rootScope.$on('cechiniApp:raportUpdate', function(event, result) {
            vm.raport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
