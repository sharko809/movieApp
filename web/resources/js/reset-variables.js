/**
 * Created by dsharko on 8/9/2016.
 */

function setLoginInputs(login) {
    document.getElementById('userName').value = login;
}

function setRegistrationInputs (name, email) {
    document.getElementById('newUserName').value = name;
    document.getElementById('newUserLogin').value = email;
}

function setMovieInputs (title, director, date, posterUrl, trailerUrl, description) {
    document.getElementById('title').value = title;
    document.getElementById('director').value = director;
    document.getElementById('releaseDate').value = date;
    document.getElementById('posterUrl').value = posterUrl;
    document.getElementById('trailerUrl').value = trailerUrl;
    document.getElementById('description').value = description;
}

function setReviewInputs (title, rating, text) {
    document.getElementById('reviewTitle').value = title;
    document.getElementById('rating').value = rating;
    document.getElementById('reviewText').value = text;
}