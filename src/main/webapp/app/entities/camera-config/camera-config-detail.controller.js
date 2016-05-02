(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('CameraConfigDetailController', CameraConfigDetailController);

    CameraConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CameraConfig'];

    function CameraConfigDetailController($scope, $rootScope, $stateParams, entity, CameraConfig) {
        var vm = this;
        vm.cameraConfig = entity;
        vm.load = function (id) {
            CameraConfig.get({id: id}, function(result) {
                vm.cameraConfig = result;
            });
        };
        var unsubscribe = $rootScope.$on('birdtheatreApp:cameraConfigUpdate', function(event, result) {
            vm.cameraConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
