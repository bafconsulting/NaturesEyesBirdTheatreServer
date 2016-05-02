'use strict';

describe('Controller Tests', function() {

    describe('YoutubeBroadcast Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYoutubeBroadcast, MockCameraConfig, MockPrivacy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYoutubeBroadcast = jasmine.createSpy('MockYoutubeBroadcast');
            MockCameraConfig = jasmine.createSpy('MockCameraConfig');
            MockPrivacy = jasmine.createSpy('MockPrivacy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YoutubeBroadcast': MockYoutubeBroadcast,
                'CameraConfig': MockCameraConfig,
                'Privacy': MockPrivacy
            };
            createController = function() {
                $injector.get('$controller')("YoutubeBroadcastDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'birdtheatreApp:youtubeBroadcastUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
