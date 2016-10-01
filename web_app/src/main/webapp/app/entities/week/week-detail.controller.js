(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('WeekDetailController', WeekDetailController);

    WeekDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Week', 'Day', 'Person'];

    function WeekDetailController($scope, $rootScope, $stateParams, previousState, entity, Week, Day, Person) {
        var vm = this;

        vm.week = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:weekUpdate', function(event, result) {
            vm.week = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
