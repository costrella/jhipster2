(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest2DetailController', Entitytest2DetailController);

    Entitytest2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Entitytest2'];

    function Entitytest2DetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Entitytest2) {
        var vm = this;

        vm.entitytest2 = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterApp:entitytest2Update', function(event, result) {
            vm.entitytest2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
