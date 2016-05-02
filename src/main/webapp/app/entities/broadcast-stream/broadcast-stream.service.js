(function() {
    'use strict';
    angular
        .module('birdtheatreApp')
        .factory('BroadcastStream', BroadcastStream);

    BroadcastStream.$inject = ['$resource', 'DateUtils'];

    function BroadcastStream ($resource, DateUtils) {
        var resourceUrl =  'api/broadcast-streams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startTimestamp = DateUtils.convertDateTimeFromServer(data.startTimestamp);
                    data.endTimestamp = DateUtils.convertDateTimeFromServer(data.endTimestamp);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
