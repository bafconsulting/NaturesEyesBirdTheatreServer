(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('dashboard', {
        parent: 'app',
        url: '/dashboard',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/socialDashboard/dashboard.html',
                controller: 'DashboardController',
                controllerAs: 'vm'
            }
        }
    });
}
})();