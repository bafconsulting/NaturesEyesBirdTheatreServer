(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('about', {
        parent: 'app',
        url: '/about',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/about/about.html',
                controller: 'AboutController',
                controllerAs: 'vm'
            }
        }
    });
}
})();