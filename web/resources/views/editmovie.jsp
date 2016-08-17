<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/10/2016
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit movie ${movie.movieName}</title>
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
    <link rel="stylesheet" href="/resources/css/admin.css">
    <link rel="stylesheet" href="/resources/css/movie.css">
    <script src="/resources/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
    <script src="/resources/js/reset-variables.js" type="text/javascript"></script>
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

<div class="pure-g custom-margin">
    <div class="pure-u-4-5 centered">
        <form id="movie-form" class="pure-form" method="post" action="/admin/editmovie">
            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <input id="title" name="title" type="text" class="pure-input-1-2" minlength="1" maxlength="30" placeholder="Title" value="${movie.movieName}" required readonly/>
                    <button id="editTitle" type="button" class="pure-button">Edit</button>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <input id="director" name="director" type="text" class="pure-input-1-2" minlength="1" maxlength="30" placeholder="Director" value="${movie.director}" readonly/>
                    <button id="editDirector" type="button" class="pure-button">Edit</button>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <input id="releaseDate" name="releaseDate" type="date" class="pure-input-1-2" min="1890-01-01" max="2150-01-01" placeholder="Release date" value="${movie.releaseDate}" readonly/>
                    <button id="editDate" type="button" class="pure-button">Edit</button>
                </div>
            </fieldset>

            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <input id="posterUrl" name="posterUrl" type="url" class="pure-input-1-2" minlength="7" maxlength="500" placeholder="Poster URL" value="${movie.posterURL}" readonly/>
                    <button id="editPoster" type="button" class="pure-button">Edit</button>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <input id="trailerUrl" name="trailerUrl" type="url" class="pure-input-1-2" minlength="7" maxlength="500" placeholder="Trailer URL" value="${movie.trailerURL}" readonly/>
                    <button id="editTrailer" type="button" class="pure-button">Edit</button>
                </div>
                <div class="pure-control-group"  style="margin-bottom: 5px;">
                    <textarea id="description" name="description" class="pure-input-1-2" minlength="5" maxlength="2000" placeholder="Description" required readonly>${movie.description}</textarea>
                    <button id="editDescription" type="button" class="pure-button">Edit</button>
                </div>
            </fieldset>
            <c:if test="${updMovie.movieName.length() >= 1}">
                <script type="text/javascript">
                    setMovieInputs(${updMovie.movieName}, ${updMovie.director}, ${updMovie.releaseDate}, ${updMovie.posterURL}, ${updMovie.trailerURL}, ${updMovie.description});
                </script>
            </c:if>
            <input type="hidden" name="movieID" value="${movie.id}"/>
            <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Update movie</button>
        </form>
        <div class="pure-u-1">
            <c:forEach items="${result}" var="r">
                ${r}<br/>
            </c:forEach>
        </div>
        <c:forEach items="${reviews}" var="review">
            <div class="review pure-u-md-1 max-width" style="margin-bottom: 5px;">
                <div class="pure-u-md-1 inline-flex max-width" style="margin: 5px;">
                    <div class="pure-u-md-6-8 pure-u-sm-6-12 max-width">
                        <strong>${review.title}</strong> by ${users.get(review.userId)}
                    </div>
                    <div class="pure-u-md-1-8 pure-u-sm-4-12" style="text-align: center;">
                        Posted: ${review.postDate}
                    </div>
                    <div class="pure-u-md-1-8 pure-u-sm-2-12" style="text-align: center;">
                        Rated: ${review.rating}/10
                    </div>
                </div>
                <div class="pure-u-md-1 max-width inline-flex" style="margin: 5px;">
                    <div class="pure-u-md-5-8 pure-u-sm-5-8 max-width" style="padding-top: 14px;">
                            ${review.reviewText}
                    </div>
                    <div class="pure-u-md-3-8 pure-u-sm-3-8" style="text-align: end; margin-right: 20px;">
                        <form method="post" action="/admin/delreview" style="margin: 0px;">
                            <input name="reviewID" type="hidden" value="${review.id}"/>
                            <input name="movieID" type="hidden" value="${movie.id}"/>
                            <button class="pure-button" type="submit">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="/resources/js/editmovie-enable-inputs.js" type="text/javascript"></script>
</body>
</html>
