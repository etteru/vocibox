'use strict';

vociboxApp.controller('ExpressionController', function ($scope, resolvedExpression, Expression) {

        $scope.expressions = resolvedExpression;

        $scope.create = function () {
            Expression.save($scope.expression,
                function () {
                    $scope.expressions = Expression.query();
                    $('#saveExpressionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.expression = Expression.get({id: id});
            $('#saveExpressionModal').modal('show');
        };

        $scope.delete = function (id) {
            Expression.delete({id: id},
                function () {
                    $scope.expressions = Expression.query();
                });
        };

        $scope.clear = function () {
            $scope.expression = {expression: null, translation: null, masculine: null, feminine: null, singular: null, plural: null, example: null, definition: null, opposite: null, comment: null, pronunciation: null, image: null, latitude: null, longitude: null, priority: null, marked: null, created: null, modified: null, id: null};
        };
    });