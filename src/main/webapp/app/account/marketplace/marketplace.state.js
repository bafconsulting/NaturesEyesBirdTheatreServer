(function() {
'use strict';

angular
    .module('natureseyesApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('marketplace', {
        parent: 'app',
        url: '/marketplace',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/account/marketplace/marketplace.html',
                controller: 'AboutController',
                controllerAs: 'vm'
            }
        }
    });
}
})();