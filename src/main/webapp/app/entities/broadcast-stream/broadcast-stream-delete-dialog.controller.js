(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .controller('BroadcastStreamDeleteController',BroadcastStreamDeleteController);

    BroadcastStreamDeleteController.$inject = ['$uibModalInstance', 'entity', 'BroadcastStream'];

    function BroadcastStreamDeleteController($uibModalInstance, entity, BroadcastStream) {
        var vm = this;
        vm.broadcastStream = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            BroadcastStream.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
