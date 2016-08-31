(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('RaportDetailController', RaportDetailController);

    RaportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Raport', 'Person', 'Store'];

    function RaportDetailController($scope, $rootScope, $stateParams, previousState, entity, Raport, Person, Store) {
        var vm = this;

        vm.raport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:raportUpdate', function(event, result) {
            vm.raport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
