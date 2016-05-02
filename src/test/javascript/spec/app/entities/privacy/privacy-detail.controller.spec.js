'use strict';

describe('Controller Tests', function() {

    describe('Privacy Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrivacy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrivacy = jasmine.createSpy('MockPrivacy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Privacy': MockPrivacy
            };
            createController = function() {
                $injector.get('$controller')("PrivacyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'birdtheatreApp:privacyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
