<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/4/2016
  Time: 2:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${movie.movieName}</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/movie.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <div id="movie-content" class="pure-u-1 inline-flex" style="margin-top: 15px; margin-bottom: 10px;">
            <div class="pure-u-md-1-3 pure-u-sm-1" style="margin: 5px;">
                <img class="pure-img" src="http://screencrush.com/files/2015/11/warcraft-poster-full.jpg"/>
            </div>
            <div class="info-class pure-u-md-2-3 pure-u-sm-1" style="margin: 5px;">
                <div class="pure-u-1">
                    <div>
                        <h4 class="inline">Title: </h4>
                        <span class="remove-underline">${movie.movieName}</span>
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
                        <h4 class="inline">Description:</h4>
                        <span>${movie.description}</span>
                    </div>
                </div>

                <div class="pure-u-1 videoWrapper">
                    <iframe src="${movie.trailerURL}" frameborder="0" allowfullscreen></iframe>
                </div>

            </div>
        </div>

        <div class="pure-u-1" style="margin: 5px;">
            <div class="review-section">
                <form class="pure-form" method="post" action="/postreview">
                    <div class="pure-u-1 inline-flex">
                        <div class="pure-u-6-8 max-width" style="margin-top: 7px;">
                            <input class="max-width" type="text"
                                   placeholder="Review title. You can SHORTLY describe your impression."/>
                        </div>
                        <div class="pure-u-1-8">
                            <div class="center-text">
                                <p>
                                    Rating:
                                </p>
                            </div>
                        </div>
                        <div class="pure-u-1-8" style="margin-top: 7px;">
                            <select class="max-width">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                    </div>
                    <%--<div>--%>
                    <div class="pure-u-1">
                        <textarea class="max-width" placeholder="Your review"></textarea>
                    </div>
                    <%--</div>--%>
                    <div class="pure-u-1">
                        <button type="submit">Submit review</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

</body>
</html>
<%--<form method="post" action="/postreview" class="pure-form">--%>
<%--<fieldset>--%>
<%--<div class="pure-g">--%>
<%--<div class="pure-u-1 pure-u-md-6-8">--%>
<%--<input type="text" placeholder="Title"/>--%>
<%--</div>--%>
<%--<div class="pure-u-1 pure-md-2-8">--%>
<%--<label for="rating">Rating</label>--%>
<%--<select id="rating">--%>
<%--<option value="1">1</option>--%>
<%--<option value="2">2</option>--%>
<%--<option value="3">3</option>--%>
<%--<option value="4">4</option>--%>
<%--<option value="5">5</option>--%>
<%--<option value="6">6</option>--%>
<%--<option value="7">7</option>--%>
<%--<option value="8">8</option>--%>
<%--<option value="9">9</option>--%>
<%--<option value="10">10</option>--%>
<%--</select>--%>
<%--</div>--%>
<%--<div class="pure-u-1 pure-u-md-1">--%>
<%--<textarea placeholder="Review text"></textarea>--%>
<%--</div>--%>
<%--<button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Submit review--%>
<%--</button>--%>
<%--</div>--%>
<%--</fieldset>--%>
<%--</form>--%>