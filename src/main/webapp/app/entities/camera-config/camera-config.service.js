(function() {
    'use strict';
    angular
        .module('birdtheatreApp')
        .factory('CameraConfig', CameraConfig);

    CameraConfig.$inject = ['$resource'];

    function CameraConfig ($resource) {
        var resourceUrl =  'api/camera-configs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
