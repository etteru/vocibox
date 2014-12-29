'use strict';

vociboxApp.controller('ExpressionController', function ($scope, resolvedExpression, Expression, resolvedTag) {

        $scope.expressions = resolvedExpression.content;
        $scope.tags = resolvedTag;

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

        $scope.random = function () {
            Expression.query({size:1, page: randomIntFromInterval(0, $scope.totalItems)}, function(page) {
                $scope.expression = page.content[0];
            });
        };

        function randomIntFromInterval(min, max){
            return Math.floor(Math.random() * (max - min + 1) + min);
        }

        $scope.currentPage = 1;
        $scope.totalItems = resolvedExpression.totalElements;

        $scope.pageChanged = function() {
            Expression.query({page: $scope.currentPage - 1}, function(page) {
              $scope.expressions = page.content;
              $scope.totalItems = page.totalElements;
            });
        };
    });

