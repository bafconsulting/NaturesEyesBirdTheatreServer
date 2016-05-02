(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('CameraConfigDialogController', CameraConfigDialogController);

    CameraConfigDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CameraConfig'];

    function CameraConfigDialogController ($scope, $stateParams, $uibModalInstance, entity, CameraConfig) {
        var vm = this;
        vm.cameraConfig = entity;
        vm.load = function(id) {
            CameraConfig.get({id : id}, function(result) {
                vm.cameraConfig = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('birdtheatreApp:cameraConfigUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cameraConfig.id !== null) {
                CameraConfig.update(vm.cameraConfig, onSaveSuccess, onSaveError);
            } else {
                CameraConfig.save(vm.cameraConfig, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
