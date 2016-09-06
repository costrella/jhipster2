(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest1Controller', Entitytest1Controller);

    Entitytest1Controller.$inject = ['$scope', '$state', 'Entitytest1'];

    function Entitytest1Controller ($scope, $state, Entitytest1) {
        var vm = this;
        
        vm.entitytest1S = [];

        loadAll();

        function loadAll() {
            Entitytest1.query(function(result) {
                vm.entitytest1S = result;
            });
        }
    }
})();
