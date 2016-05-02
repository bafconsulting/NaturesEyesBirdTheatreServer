'use strict';

describe('Controller Tests', function() {

    describe('BroadcastStream Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBroadcastStream, MockCameraConfig;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBroadcastStream = jasmine.createSpy('MockBroadcastStream');
            MockCameraConfig = jasmine.createSpy('MockCameraConfig');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BroadcastStream': MockBroadcastStream,
                'CameraConfig': MockCameraConfig
            };
            createController = function() {
                $injector.get('$controller')("BroadcastStreamDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'birdtheatreApp:broadcastStreamUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
