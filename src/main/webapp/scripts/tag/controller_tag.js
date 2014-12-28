'use strict';

vociboxApp.controller('TagController', function ($scope, resolvedTag, Tag, resolvedExpression) {

        $scope.tags = resolvedTag;
        $scope.expressions = resolvedExpression;

        $scope.create = function () {
            Tag.save($scope.tag,
                function () {
                    $scope.tags = Tag.query();
                    $('#saveTagModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.tag = Tag.get({id: id});
            $('#saveTagModal').modal('show');
        };

        $scope.delete = function () {
            Tag.delete({id: $scope.tag.id},
                function () {
                    $scope.tags = Tag.query();
                    $('#deleteTagModal').modal('hide');
                });
        };

        $scope.clear = function () {
            $scope.tag = {name: null, id: null};
        };

        $scope.showDeleteModal = function (id) {
            $scope.tag = Tag.get({id: id});
            $('#deleteTagModal').modal('show');
        };
    });
