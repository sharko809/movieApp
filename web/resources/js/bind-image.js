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

    $scope.imgurl = [{url: ""}];

    $scope.setInp = function (title, director, date, posterUrl, trailerUrl, description) {
        if ($scope.imgurl.url === "") {
            return;
        }
        var movieTitle = document.getElementById('title');
        var movieDirector = document.getElementById('director');
        var movieReleaseDate = document.getElementById('releaseDate');
        var moviePosterUrl = document.getElementById('posterUrl');
        var movieTrailerUrl = document.getElementById('trailerUrl');
        var movieDescriptionUrl = document.getElementById('description');
        if (movieTitle != null && movieDirector != null && movieReleaseDate != null && moviePosterUrl != null
            && movieTrailerUrl != null && movieDescriptionUrl != null) {
            movieTitle.value = title;
            movieDirector.value = director;
            movieReleaseDate.value = date;
            $scope.imgurl = [{url: posterUrl}];
            // moviePosterUrl.value = posterUrl;
            movieTrailerUrl.value = trailerUrl;
            movieDescriptionUrl.value = description;
        }
    };

});