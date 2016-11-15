(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('warehouse', {
            parent: 'entity',
            url: '/warehouse?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Warehouses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/warehouse/warehouses.html',
                    controller: 'WarehouseController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('warehouse-detail', {
            parent: 'entity',
            url: '/warehouse/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Warehouse'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/warehouse/warehouse-detail.html',
                    controller: 'WarehouseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Warehouse', function($stateParams, Warehouse) {
                    return Warehouse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'warehouse',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('warehouse-detail.edit', {
            parent: 'warehouse-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/warehouse/warehouse-dialog.html',
                    controller: 'WarehouseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Warehouse', function(Warehouse) {
                            return Warehouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('warehouse.new', {
            parent: 'warehouse',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/warehouse/warehouse-dialog.html',
                    controller: 'WarehouseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('warehouse', null, { reload: 'warehouse' });
                }, function() {
                    $state.go('warehouse');
                });
            }]
        })
        .state('warehouse.edit', {
            parent: 'warehouse',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/warehouse/warehouse-dialog.html',
                    controller: 'WarehouseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Warehouse', function(Warehouse) {
                            return Warehouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('warehouse', null, { reload: 'warehouse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('warehouse.delete', {
            parent: 'warehouse',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/warehouse/warehouse-delete-dialog.html',
                    controller: 'WarehouseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Warehouse', function(Warehouse) {
                            return Warehouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('warehouse', null, { reload: 'warehouse' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
