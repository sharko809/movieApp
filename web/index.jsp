<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movies list</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.1.0.js"
            integrity="sha256-slogkvB1K3VOkzAI8QITxV3VzpOnkeNVsKvtkYLMjfk=" crossorigin="anonymous"></script>
    <script src="/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body style="background-color:beige">
<header class="header fixed z-index">
    <div class="header-inner">
        <form class="float-right">
            <input placeholder="Search (not yet implemented)" style="border-radius: 4px;">
        </form>
    </div>
</header>
<div class="padding-top"></div>
<div class="container" style="width: 80%">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <form method="post" action="/login">
            Username: <input type="text" name="userName"/><br/>
            Password: <input type="password" name="userPassword"/><br/>
            <input type="submit" value="Login"/>
        </form>
        <c:forEach items="${movies}" var="movie">
            <div class="movie-container">
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
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
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <iframe class="video-container" <%--width="560" height="315"--%> src="${movie.trailerURL}"
                            frameborder="0"
                            allowfullscreen></iframe>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>