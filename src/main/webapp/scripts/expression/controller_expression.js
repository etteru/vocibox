'use strict';

vociboxApp.controller('ExpressionController', function ($scope, resolvedExpression, Expression, resolvedTag) {

        $scope.expressions = resolvedExpression.content;
        $scope.paginationCurrentPage = 1;
        setPaginationVariables(resolvedExpression);

        $scope.tags = resolvedTag;
        $scope.submittedFilter = {priorities: null, marked: null, tags: null};

        $scope.search = function () {
            $scope.submittedFilter.priorities = $scope.filter.priorities;
            $scope.submittedFilter.marked = $scope.filter.marked;
            $scope.submittedFilter.tags = $scope.filter.tags;
            getExpressions({}, function(page) {
                setPaginationVariables(page);
            });
            $scope.clear();
            $scope.paginationCurrentPage = 1;
        };

        $scope.resetFilter = function () {
            $scope.filter = {};
            $scope.filter.priorities = null;
            $scope.filter.marked = null;
            $scope.filter.tags = null;
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
                    getExpressions({page: $scope.paginationCurrentPage - 1}, function(page) {
                        setPaginationVariables(page);
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
                    getExpressions({page: $scope.paginationCurrentPage - 1}, function(page) {
                        setPaginationVariables(page);
                    });
                    $('#deleteExpressionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.randomMode = false;
            $scope.translationOnly = false;
            $scope.expression = {expression: null, translation: null, masculine: null, feminine: null, neuter: null, singular: null,
                plural: null, example: null, definition: null, opposite: null, comment: null, pronunciation: null,
                image: null, latitude: null, longitude: null, priority: 3, marked: false,
                createdDate: null, lastModifiedDate: null, id: null};
        };

        $scope.random = function () {
            $scope.randomMode = true;
            $scope.translationOnly = true;
            getExpressions({size:1, page: randomIntFromInterval(0, $scope.paginationTotalItems - 1)}, function(page) {
                $scope.update(page.content[0].id); // make sure tags are fetched as well
            });
        };

        function randomIntFromInterval(min, max){
            return Math.floor(Math.random() * (max - min + 1) + min);
        }

        function setPaginationVariables(page){
            $scope.expressions = page.content;
            $scope.paginationTotalItems = page.totalElements;
            if (page.totalElements == 0){
                $scope.paginationFrom = 0;
                $scope.paginationTo = 0;
            }
            else {
                $scope.paginationFrom = (page.number * page.size) + 1;
                $scope.paginationTo = $scope.paginationFrom + page.numberOfElements - 1;
            }
        }

        $scope.pageChanged = function() {
            getExpressions({page: $scope.paginationCurrentPage - 1}, function(page) {
                setPaginationVariables(page);
            });
        };

        function getExpressions(params, callback){
            params.priorities = $scope.submittedFilter.priorities;
            params.marked = $scope.submittedFilter.marked;
            params.tags = getIds($scope.submittedFilter.tags);
            Expression.query(params, callback);
        }

});


