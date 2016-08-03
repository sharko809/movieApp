<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/3/2016
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movies</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-3-4 centered">
        <c:forEach items="${movies}" var="movie">
            <div class="pure-u-1">
                <div class="movie-container">
                    <div class="pure-u-lg-1-2 pure-u-sm-1 movie-info">
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
                    <div class="pure-u-lg-1-2 pure-u-sm-1 movie-trailer">
                        <iframe width="100%" height="315" src="${movie.trailerURL}" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
