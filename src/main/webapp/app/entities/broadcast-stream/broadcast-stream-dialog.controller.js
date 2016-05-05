(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('BroadcastStreamDialogController', BroadcastStreamDialogController);

    BroadcastStreamDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'BroadcastStream'];

    function BroadcastStreamDialogController ($scope, $stateParams, $uibModalInstance, entity, BroadcastStream) {
        var vm = this;
        vm.broadcastStream = entity;
        vm.load = function(id) {
            BroadcastStream.get({id : id}, function(result) {
                vm.broadcastStream = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('birdtheatreApp:broadcastStreamUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.broadcastStream.id !== null) {
                BroadcastStream.update(vm.broadcastStream, onSaveSuccess, onSaveError);
            } else {
                BroadcastStream.save(vm.broadcastStream, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startTimestamp = false;
        vm.datePickerOpenStatus.endTimestamp = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
