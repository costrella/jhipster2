(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest1DetailController', Entitytest1DetailController);

    Entitytest1DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entitytest1'];

    function Entitytest1DetailController($scope, $rootScope, $stateParams, previousState, entity, Entitytest1) {
        var vm = this;

        vm.entitytest1 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:entitytest1Update', function(event, result) {
            vm.entitytest1 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
