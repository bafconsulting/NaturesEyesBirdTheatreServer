(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('BroadcastStreamDetailController', BroadcastStreamDetailController);

    BroadcastStreamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BroadcastStream', 'CameraConfig'];

    function BroadcastStreamDetailController($scope, $rootScope, $stateParams, entity, BroadcastStream, CameraConfig) {
        var vm = this;
        vm.broadcastStream = entity;
        vm.load = function (id) {
            BroadcastStream.get({id: id}, function(result) {
                vm.broadcastStream = result;
            });
        };
        var unsubscribe = $rootScope.$on('birdtheatreApp:broadcastStreamUpdate', function(event, result) {
            vm.broadcastStream = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
