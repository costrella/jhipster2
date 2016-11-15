(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoregroupDetailController', StoregroupDetailController);

    StoregroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Storegroup', 'Store'];

    function StoregroupDetailController($scope, $rootScope, $stateParams, previousState, entity, Storegroup, Store) {
        var vm = this;

        vm.storegroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cechiniApp:storegroupUpdate', function(event, result) {
            vm.storegroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
