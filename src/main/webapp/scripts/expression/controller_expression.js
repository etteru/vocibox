'use strict';

vociboxApp.controller('ExpressionController', function ($scope, resolvedExpression, Expression) {

        $scope.expressions = resolvedExpression.content;

        $scope.create = function () {
            Expression.save($scope.expression,
                function () {
                    Expression.query({}, function(page) {
                        $scope.expressions = page.content;
                        $scope.totalItems = page.totalElements;
                    });
                    $('#saveExpressionModal').modal('hide');
                    $scope.clear();
                    $scope.currentPage = 1;
                });
        };

        $scope.update = function (id) {
            $scope.expression = Expression.get({id: id});
            $('#saveExpressionModal').modal('show');
        };

        $scope.showDeleteModal = function (id) {
            $scope.expression = Expression.get({id: id});
            $('#deleteExpressionModal').modal('show');
        };

        $scope.delete = function () {
            Expression.delete({id: $scope.expression.id},
                function () {
                    Expression.query({page: $scope.currentPage - 1}, function(page) {
                        $scope.expressions = page.content;
                        $scope.totalItems = page.totalElements;
                    });
                    $('#deleteExpressionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.expression = {expression: null, translation: null, masculine: null, feminine: null, singular: null,
                plural: null, example: null, definition: null, opposite: null, comment: null, pronunciation: null,
                image: null, latitude: null, longitude: null, priority: null, marked: null,
                createdDate: null, lastModifiedDate: null, id: null};
        };

        $scope.currentPage = 1;
        $scope.totalItems = resolvedExpression.totalElements;

        $scope.pageChanged = function() {
            Expression.query({page: $scope.currentPage - 1}, function(page) {
              $scope.expressions = page.content;
              $scope.totalItems = page.totalElements;
            });
        };
    });

