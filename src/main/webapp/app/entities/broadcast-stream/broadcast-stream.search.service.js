(function() {
    'use strict';

    angular
        .module('birdtheatreApp')
        .factory('BroadcastStreamSearch', BroadcastStreamSearch);

    BroadcastStreamSearch.$inject = ['$resource'];

    function BroadcastStreamSearch($resource) {
        var resourceUrl =  'api/_search/broadcast-streams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
