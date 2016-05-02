(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('tasks', {
        parent: 'app',
        url: '/tasks',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/tasks/tasks.html',
                controller: 'TasksController',
                controllerAs: 'vm'
            }
        }
    });
}
})();