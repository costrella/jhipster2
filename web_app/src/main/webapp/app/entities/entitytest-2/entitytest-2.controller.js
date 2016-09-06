(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('Entitytest2Controller', Entitytest2Controller);

    Entitytest2Controller.$inject = ['$scope', '$state', 'DataUtils', 'Entitytest2'];

    function Entitytest2Controller ($scope, $state, DataUtils, Entitytest2) {
        var vm = this;
        
        vm.entitytest2S = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Entitytest2.query(function(result) {
                vm.entitytest2S = result;
            });
        }
    }
})();
