'use strict';

vociboxApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/tag', {
                    templateUrl: 'views/tags.html',
                    controller: 'TagController',
                    resolve:{
                        resolvedTag: ['Tag', function (Tag) {
                            return Tag.query().$promise;
                        }],
                        resolvedExpression: ['Expression', function (Expression) {
                            return Expression.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
