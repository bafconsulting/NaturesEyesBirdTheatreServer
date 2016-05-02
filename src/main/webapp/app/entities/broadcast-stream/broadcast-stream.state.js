(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('broadcast-stream', {
            parent: 'entity',
            url: '/broadcast-stream?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BroadcastStreams'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/broadcast-stream/broadcast-streams.html',
                    controller: 'BroadcastStreamController',
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
        .state('broadcast-stream-detail', {
            parent: 'entity',
            url: '/broadcast-stream/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BroadcastStream'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/broadcast-stream/broadcast-stream-detail.html',
                    controller: 'BroadcastStreamDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BroadcastStream', function($stateParams, BroadcastStream) {
                    return BroadcastStream.get({id : $stateParams.id});
                }]
            }
        })
        .state('broadcast-stream.new', {
            parent: 'broadcast-stream',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/broadcast-stream/broadcast-stream-dialog.html',
                    controller: 'BroadcastStreamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                startTimestamp: null,
                                endTimestamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('broadcast-stream', null, { reload: true });
                }, function() {
                    $state.go('broadcast-stream');
                });
            }]
        })
        .state('broadcast-stream.edit', {
            parent: 'broadcast-stream',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/broadcast-stream/broadcast-stream-dialog.html',
                    controller: 'BroadcastStreamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BroadcastStream', function(BroadcastStream) {
                            return BroadcastStream.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('broadcast-stream', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('broadcast-stream.delete', {
            parent: 'broadcast-stream',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/broadcast-stream/broadcast-stream-delete-dialog.html',
                    controller: 'BroadcastStreamDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BroadcastStream', function(BroadcastStream) {
                            return BroadcastStream.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('broadcast-stream', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
