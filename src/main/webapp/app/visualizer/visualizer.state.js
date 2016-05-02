(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .config(stateConfig);

stateConfig.$inject = ['$stateProvider'];

function stateConfig($stateProvider) {
    $stateProvider.state('visualizer', {
        parent: 'app',
        url: '/visualizer',
        data: {
            authorities: []
        },
        views: {
            'content@': {
                templateUrl: 'app/visualizer/visualizer.html',
                controller: 'VisualizerController',
                controllerAs: 'vm'
            }
        }
    });
}
})();