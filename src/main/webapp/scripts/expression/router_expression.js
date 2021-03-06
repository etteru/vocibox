'use strict';

vociboxApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/expression', {
                    templateUrl: 'views/expressions.html',
                    controller: 'ExpressionController',
                    resolve:{
                        resolvedExpression: ['Expression', function (Expression) {
                            return Expression.query().$promise;
                        }],
                        resolvedTag: ['Tag', function (Tag) {
                            return Tag.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
