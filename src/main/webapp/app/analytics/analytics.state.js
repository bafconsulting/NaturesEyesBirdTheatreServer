(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('analytics', {
        parent: 'app',
        url: '/analytics',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/analytics/analytics.html',
                controller: 'Analyticsontroller',
                controllerAs: 'vm'
            }
        }
    });
}
})();