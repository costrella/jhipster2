(function() {
    'use strict';

    angular
        .module('cechiniApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('storegroup', {
            parent: 'entity',
            url: '/storegroup?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Storegroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/storegroup/storegroups.html',
                    controller: 'StoregroupController',
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
        .state('storegroup-detail', {
            parent: 'entity',
            url: '/storegroup/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Storegroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/storegroup/storegroup-detail.html',
                    controller: 'StoregroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Storegroup', function($stateParams, Storegroup) {
                    return Storegroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'storegroup',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('storegroup-detail.edit', {
            parent: 'storegroup-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/storegroup/storegroup-dialog.html',
                    controller: 'StoregroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Storegroup', function(Storegroup) {
                            return Storegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('storegroup.new', {
            parent: 'storegroup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/storegroup/storegroup-dialog.html',
                    controller: 'StoregroupDialogController',
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
                    $state.go('storegroup', null, { reload: 'storegroup' });
                }, function() {
                    $state.go('storegroup');
                });
            }]
        })
        .state('storegroup.edit', {
            parent: 'storegroup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/storegroup/storegroup-dialog.html',
                    controller: 'StoregroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Storegroup', function(Storegroup) {
                            return Storegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('storegroup', null, { reload: 'storegroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('storegroup.delete', {
            parent: 'storegroup',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/storegroup/storegroup-delete-dialog.html',
                    controller: 'StoregroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Storegroup', function(Storegroup) {
                            return Storegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('storegroup', null, { reload: 'storegroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
