(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Store', 'Raport', 'Week'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Store, Raport, Week) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
