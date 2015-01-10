'use strict';

vociboxApp.controller('ExpressionController', function ($scope, resolvedExpression, Expression, resolvedTag) {

        $scope.expressions = resolvedExpression.content;
        $scope.tags = resolvedTag;
        $scope.submittedFilter = {priorities: null, marked: null, tags: null};

        $scope.search = function () {
            $scope.submittedFilter.priorities = $scope.filter.priorities;
            $scope.submittedFilter.marked = $scope.filter.marked;
            $scope.submittedFilter.tags = $scope.filter.tags;
            getExpressions({}, function(page) {
                $scope.expressions = page.content;
                $scope.totalItems = page.totalElements;
            });
            $scope.clear();
            $scope.currentPage = 1;
        };

        function getIds(tags){
            var ids = [];
            for (var key in tags) {
                var tag = tags[key];
                ids.push(tag.id);
            }
            return ids;
        }

        $scope.create = function () {
            Expression.save($scope.expression,
                function () {
                    getExpressions({}, function(page) {
                        $scope.expressions = page.content;
                        $scope.totalItems = page.totalElements;
                    });
                    $('#saveExpressionModal').modal('hide');
                    $scope.clear();
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
                    getExpressions({page: $scope.currentPage - 1}, function(page) {
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
                image: null, latitude: null, longitude: null, priority: 3, marked: false,
                createdDate: null, lastModifiedDate: null, id: null};
        };

        $scope.random = function () {
            getExpressions({size:1, page: randomIntFromInterval(0, $scope.totalItems - 1)}, function(page) {
                $scope.expression = page.content[0];
            });
        };

        function randomIntFromInterval(min, max){
            return Math.floor(Math.random() * (max - min + 1) + min);
        }

        $scope.currentPage = 1;
        $scope.totalItems = resolvedExpression.totalElements;

        $scope.pageChanged = function() {
            getExpressions({page: $scope.currentPage - 1}, function(page) {
              $scope.expressions = page.content;
              $scope.totalItems = page.totalElements;
            });
        };

        function getExpressions(params, callback){
            params.priorities = $scope.submittedFilter.priorities;
            params.marked = $scope.submittedFilter.marked;
            params.tags = getIds($scope.submittedFilter.tags);
            Expression.query(params, callback);
        }
    });

