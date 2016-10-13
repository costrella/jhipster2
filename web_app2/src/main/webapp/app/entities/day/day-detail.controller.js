(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('DayDetailController', DayDetailController);

    DayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Day', 'Week', 'Store'];

    function DayDetailController($scope, $rootScope, $stateParams, previousState, entity, Day, Week, Store) {
        var vm = this;

        vm.day = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cechiniApp:dayUpdate', function(event, result) {
            vm.day = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
