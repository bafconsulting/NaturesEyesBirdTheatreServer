(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .factory('CameraConfigSearch', CameraConfigSearch);

    CameraConfigSearch.$inject = ['$resource'];

    function CameraConfigSearch($resource) {
        var resourceUrl =  'api/_search/camera-configs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
