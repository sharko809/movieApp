/**
 * Created by dsharko on 8/17/2016.
 */

document.getElementById('editTitle').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('title').removeAttribute('readonly');
});

document.getElementById('editDirector').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('director').removeAttribute('readonly');
});

document.getElementById('editDate').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('releaseDate').removeAttribute('readonly');
});

document.getElementById('editPoster').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('posterUrl').removeAttribute('readonly');
});

document.getElementById('editTrailer').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('trailerUrl').removeAttribute('readonly');
});

document.getElementById('editDescription').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('description').removeAttribute('readonly');
});