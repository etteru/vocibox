'use strict';

vociboxApp.factory('Expression', function ($resource) {
        return $resource('app/rest/expressions/:id?page=:page&size=:size', {page: 0, size: 10}, {
            'query': { method: 'GET'},
            'get': { method: 'GET'}
        });
    });
