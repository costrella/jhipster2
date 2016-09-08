(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entitytest-1', {
            parent: 'entity',
            url: '/entitytest-1',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.entitytest1.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-s.html',
                    controller: 'Entitytest1Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitytest1');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entitytest-1-detail', {
            parent: 'entity',
            url: '/entitytest-1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.entitytest1.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-detail.html',
                    controller: 'Entitytest1DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitytest1');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entitytest1', function($stateParams, Entitytest1) {
                    return Entitytest1.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entitytest-1',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entitytest-1-detail.edit', {
            parent: 'entitytest-1-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-dialog.html',
                    controller: 'Entitytest1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitytest1', function(Entitytest1) {
                            return Entitytest1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitytest-1.new', {
            parent: 'entitytest-1',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-dialog.html',
                    controller: 'Entitytest1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                test1: null,
                                string123: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entitytest-1', null, { reload: 'entitytest-1' });
                }, function() {
                    $state.go('entitytest-1');
                });
            }]
        })
        .state('entitytest-1.edit', {
            parent: 'entitytest-1',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-dialog.html',
                    controller: 'Entitytest1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitytest1', function(Entitytest1) {
                            return Entitytest1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitytest-1', null, { reload: 'entitytest-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitytest-1.delete', {
            parent: 'entitytest-1',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-1/entitytest-1-delete-dialog.html',
                    controller: 'Entitytest1DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entitytest1', function(Entitytest1) {
                            return Entitytest1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitytest-1', null, { reload: 'entitytest-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
