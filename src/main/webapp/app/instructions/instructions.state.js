(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('instructions', {
        parent: 'app',
        url: '/instructions',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/instructions/instructions.html',
                controller: 'InstructionsController',
                controllerAs: 'vm'
            }
        }
    });
}
})();