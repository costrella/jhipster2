(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .controller('StoreDetailController', StoreDetailController);

    StoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Store', 'Person', 'Raport', 'Day'];

    function StoreDetailController($scope, $rootScope, $stateParams, previousState, entity, Store, Person, Raport, Day) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cechiniApp:storeUpdate', function(event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
