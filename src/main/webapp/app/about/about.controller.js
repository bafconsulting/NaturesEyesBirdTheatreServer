(function() {
'use strict';

angular
    .module('birdtheatreApp')
    .controller('AboutController', AboutController);

AboutController.$inject = ['$timeout', 'Auth', 'LoginService'];

function AboutController ($timeout, Auth, LoginService) {
    var vm = this;

    vm.account = null;
    vm.isAuthenticated = null;
    vm.login = LoginService.open;
    $scope.$on('authenticationSuccess', function() {
        getAccount();
    });

    getAccount();

    function getAccount() {
        Principal.identity().then(function(account) {
            vm.account = account;
            vm.isAuthenticated = Principal.isAuthenticated;
        });
    }
}
})();