<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/3/2016
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new movie</title>
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
    <link rel="stylesheet" href="/resources/css/admin.css">
    <link rel="stylesheet" href="/resources/css/mainPage.css">
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
<div class="pure-g">
    <div class="pure-u-2-5 centered">
        <form class="pure-form" method="post" action="/admin/addmovie">
            <fieldset class="pure-group">
                <input id="title" name="title" type="text" class="pure-input-1-2" placeholder="Title" required/>
                <input id="director" name="director" type="text" class="pure-input-1-2" placeholder="Director"/>
                <input id="releaseDate" name="releaseDate" type="date" class="pure-input-1-2"
                       placeholder="Release date"/>
            </fieldset>

            <fieldset class="pure-group">
                <input id="posterUrl" name="posterUrl" type="url" class="pure-input-1-2" placeholder="Poster URL"/>
                <input id="trailerUrl" name="trailerUrl" type="url" class="pure-input-1-2" placeholder="Trailer URL"/>
                <textarea id="description" name="description" class="pure-input-1-2" placeholder="Description" required></textarea>
            </fieldset>
            <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Add movie</button>
            <c:if test="${movie.movieName.length() > 1}">
                <script type="text/javascript">
                    setMovieInputs('${movie.movieName}', '${movie.director}', '${movie.releaseDate}', '${movie.posterURL}', '${movie.trailerURL}', '${movie.description}');
                </script>
            </c:if>
        </form>
    </div>
    <div class="pure-u-1 centered">
        <c:forEach items="${result}" var="r">
            ${r}<br/>
        </c:forEach>
    </div>
</div>
<script src="/resources/js/pure/ui.js" type="text/javascript"></script>
</body>
</html>
