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
                <c:choose>
                    <c:when test="${user == null}">
                        <div class="pure-u-md-1 pure-u-sm-1">
                            <div style="margin-bottom: 5px; text-align: center">
                                Please, sign in to write reviews.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form class="pure-form" method="post" action="/postreview" onsubmit="setTimeout(function () { window.location.reload(); }, 10)">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-6-8 max-width" style="margin-top: 7px;">
                                    <input class="max-width" type="text" name="reviewTitle" placeholder="Review title. You can SHORTLY describe your impression."/>
                                </div>
                                <div class="pure-u-1-8">
                                    <div class="center-text">
                                        <p>
                                            Rating:
                                        </p>
                                    </div>
                                </div>
                                <div class="pure-u-1-8" style="margin-top: 7px;">
                                    <select class="max-width" name="userRating">
                                        <option value="1" selected>1</option>
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
                            <div class="pure-u-1" style="height: 200px; margin-bottom: 5px;">
                                <textarea style="height: 100%;" class="max-width" name="reviewText" placeholder="Your review"></textarea>
                                <input type="hidden" name="movieID" value="${movie.id}"/>
                                <input type="hidden" name="from_" value="${pageContext.request.requestURI}"/>
                                <input type="hidden" name="from" value="${pageContext.request.queryString}"/>
                            </div>
                            <div class="pure-u-1 inline-flex">
                                <div>
                                    <button id="submitReview" class="pure-button" type="submit">Post review</button>
                                </div>
                                <div style="margin-left: 5px;">
                                    <c:forEach items="${reviewError}" var="error">
                                        ${error}<br/>
                                    </c:forEach>
                                </div>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
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
                        <div class="pure-u-md-1 max-width" style="margin: 5px;">
                            <div class="pure-u-md-1 pure-u-sm-1 max-width">
                                    ${review.reviewText}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
</div>

</body>
</html>