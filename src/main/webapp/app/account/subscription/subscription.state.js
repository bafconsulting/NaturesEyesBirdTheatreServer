(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('subscription', {
        parent: 'app',
        url: '/subscription',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/account/subscription/subscription.html',
                controller: 'SubscriptionController',
                controllerAs: 'vm'
            }
        }
    });
}
})();