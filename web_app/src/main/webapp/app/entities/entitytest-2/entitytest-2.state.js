(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entitytest-2', {
            parent: 'entity',
            url: '/entitytest-2',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.entitytest2.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-s.html',
                    controller: 'Entitytest2Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitytest2');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entitytest-2-detail', {
            parent: 'entity',
            url: '/entitytest-2/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.entitytest2.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-detail.html',
                    controller: 'Entitytest2DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitytest2');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entitytest2', function($stateParams, Entitytest2) {
                    return Entitytest2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entitytest-2',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entitytest-2-detail.edit', {
            parent: 'entitytest-2-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-dialog.html',
                    controller: 'Entitytest2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitytest2', function(Entitytest2) {
                            return Entitytest2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitytest-2.new', {
            parent: 'entitytest-2',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-dialog.html',
                    controller: 'Entitytest2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                test1: null,
                                test1ContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entitytest-2', null, { reload: 'entitytest-2' });
                }, function() {
                    $state.go('entitytest-2');
                });
            }]
        })
        .state('entitytest-2.edit', {
            parent: 'entitytest-2',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-dialog.html',
                    controller: 'Entitytest2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitytest2', function(Entitytest2) {
                            return Entitytest2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitytest-2', null, { reload: 'entitytest-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitytest-2.delete', {
            parent: 'entitytest-2',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitytest-2/entitytest-2-delete-dialog.html',
                    controller: 'Entitytest2DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entitytest2', function(Entitytest2) {
                            return Entitytest2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitytest-2', null, { reload: 'entitytest-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
