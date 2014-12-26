'use strict';

vociboxApp.factory('Expression', function ($resource) {
        return $resource('app/rest/expressions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
