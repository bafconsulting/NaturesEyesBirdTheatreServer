'use strict';

describe('Controller Tests', function() {

    describe('CameraConfig Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCameraConfig;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCameraConfig = jasmine.createSpy('MockCameraConfig');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CameraConfig': MockCameraConfig
            };
            createController = function() {
                $injector.get('$controller')("CameraConfigDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'birdtheatreApp:cameraConfigUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
