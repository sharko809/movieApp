<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/9/2016
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movie management</title>
    <link rel="stylesheet" href="/resources/css/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-old-ie-min.css">
    <link rel="stylesheet" href="/resources/css/pure/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-min.css">
    <link rel="stylesheet" href="/resources/css/pure/layouts/side-menu.css">
    <!--<![endif]-->
    <link rel="stylesheet" href="/resources/css/mainPage.css">
    <script src="/resources/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div id="layout">
    <a href="#menu" id="menuLink" class="menu-link" style="top: 46px;">
        <!-- Hamburger icon -->
        <span></span>
    </a>
    <div id="menu" class="active" style="top: 46px;">
        <div class="pure-menu">
            <a class="pure-menu-heading" href="/admin">Admin tools</a>

            <ul class="pure-menu-list">
                <li class="menu-item"><a href="/admin/addmovie" class="pure-menu-link">Add movie</a></li>
                <li class="menu-item"><a href="/admin/managemovies" class="pure-menu-link">Manage movies</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <c:forEach items="${movies}" var="movie">
            <div class="pure-u-1" style="height: 100px; margin: 10px;">
                <div class="movie-container inline-flex" style="background-color: #cad2d3; border-radius: 5px; height: inherit;">
                    <div class="pure-u-lg-11-24 pure-u-sm-2-5" style="margin: 15px;">
                        <div>
                            <h4 class="inline">Title: </h4>
                            <a class="remove-link-style" href="/movies?movieId=${movie.id}">
                                    ${movie.movieName}
                            </a>
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
                            <c:choose>
                                <c:when test="${movie.rating <= 0.0}">
                                    <span>Not enough votes yet</span>
                                </c:when>
                                <c:otherwise>
                                    <span>${movie.rating}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="pure-u-lg-11-24 pure-u-sm-2-5">
                        <c:choose>
                            <c:when test="${movie.posterURL != null}">
                                <img style="height: 100px; float: right; margin-right: 10px;" class="pure-img" src="${movie.posterURL}"/>
                            </c:when>
                            <c:otherwise>
                                <img style="height: 100px; float: right; margin-right: 10px;" class="pure-img" src="/resources/images/no-poster-available.png"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="pure-u-lg-2-24 pure-u-sm-1-5">
                        <form method="post" action="/admin/updrating">
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value="" />
                            <input type="hidden" name="movieID" value="${movie.id}"/>
                            <button type="submit" class="pure-button" title="Recalculates movie rating">Rating</button>
                        </form>
                        <button class="pure-button" title="Allows to edit movie data">Edit</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>