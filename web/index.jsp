<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movies list</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css" rel="stylesheet">
</head>
<body style="background-color:beige">
<div class="container">
    <h4>test</h4>
    <div class="movie-list">
        <c:forEach items="${movies}" var="movie">
            <div class="movie-container">
                <div class="movie-info">
                    <div>
                        <h4 class="inline">Title: </h4>
                        <span>${movie.movieName}</span>
                    </div>
                    <div>
                        <h4 class="inline">Director: </h4>
                        <span>${movie.director}</span>
                    </div>
                    <div>
                        <h4 class="inline">Release Date: </h4>
                        <span>${movie.releaseDate}</span>
                    </div>
                    <div>
                        <h4 class="inline">Rating: </h4>
                        <span>${movie.rating}</span>
                    </div>
                    <div>
                        <h4 class="inline">Descrition:</h4>
                        <span>${movie.description}</span>
                    </div>
                </div>
                <div class="movie-trailer">
                    <iframe class="video-container" width="560" height="315" src="${movie.trailerURL}" frameborder="0"
                            allowfullscreen></iframe>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>