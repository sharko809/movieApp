/**
 * Created by dsharko on 8/18/2016.
 */
var app = angular.module("app", []);

app.controller('imgCtrl', function ($scope) {

    $scope.checkUrl = function () {
        var value = document.getElementById('posterUrl').value;
        if (value === '') {
            return 'a';
        } else {
            return 't';
        }
    };

});