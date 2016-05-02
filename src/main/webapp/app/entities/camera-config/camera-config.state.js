(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('camera-config', {
            parent: 'entity',
            url: '/camera-config?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CameraConfigs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera-config/camera-configs.html',
                    controller: 'CameraConfigController',
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
        .state('camera-config-detail', {
            parent: 'entity',
            url: '/camera-config/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CameraConfig'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera-config/camera-config-detail.html',
                    controller: 'CameraConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CameraConfig', function($stateParams, CameraConfig) {
                    return CameraConfig.get({id : $stateParams.id});
                }]
            }
        })
        .state('camera-config.new', {
            parent: 'camera-config',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-config/camera-config-dialog.html',
                    controller: 'CameraConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hostname: null,
                                ipaddress: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('camera-config', null, { reload: true });
                }, function() {
                    $state.go('camera-config');
                });
            }]
        })
        .state('camera-config.edit', {
            parent: 'camera-config',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-config/camera-config-dialog.html',
                    controller: 'CameraConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CameraConfig', function(CameraConfig) {
                            return CameraConfig.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera-config', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('camera-config.delete', {
            parent: 'camera-config',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-config/camera-config-delete-dialog.html',
                    controller: 'CameraConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CameraConfig', function(CameraConfig) {
                            return CameraConfig.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera-config', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
