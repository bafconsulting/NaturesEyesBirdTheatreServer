(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('CameraConfigDeleteController',CameraConfigDeleteController);

    CameraConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'CameraConfig'];

    function CameraConfigDeleteController($uibModalInstance, entity, CameraConfig) {
        var vm = this;
        vm.cameraConfig = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CameraConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
