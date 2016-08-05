<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/3/2016
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/header.css" rel="stylesheet">
</head>
<body>
<header class="pure-g header fixed z-index">
    <div class="pure-u-md-4-5 pure-u-sm-1 centered inline-flex">
        <div class="pure-u-1 inline-flex">

            <c:choose>
                <c:when test="${user == null}">
                    <div id="login-form" class="pure-u-md-3-8 pure-u-sm-3-8 max-width">
                        <form class="pure-form inline-flex" method="post" action="/login"
                              onsubmit="setTimeout(function () { window.location.reload(); }, 10)" style="margin: 5px auto 5px auto;">
                            <input class="pure-input-1-2 input-margin" type="text" name="userLogin" placeholder="E-mail"/><br/>
                            <input class="pure-input-1-2 input-margin" type="password" name="userPassword" placeholder="Password"/><br/>
                            <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
                            <input type="image" src="login-32.ico" alt="Submit"/>
                            <%--<button class="pure-button" type="submit">Post review</button>--%>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="user-welcome" class="pure-u-md-3-8 pure-u-sm-3-8 max-width">
                        <p class="menu-text">
                            Logged in as
                            <a class="menu-text" href="/account?user=${user.id}">
                                    ${user.name}.
                            </a>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>


            <div id="top-rated" class="pure-u-md-1-8 pure-u-sm-1-8 max-width" style="text-align: center;">
                <a class="menu-text" href="/toprated">
                    <p>
                        Top rated
                    </p>
                </a>
            </div>

            <div id="movie-list" class="pure-u-md-1-8 pure-u-sm-1-8 max-width" style="text-align: center;">
                <a class="menu-text" href="/movielist">
                    <p>
                        Movie list
                    </p>
                </a>
            </div>

            <form class="pure-u-md-2-8 pure-u-sm-2-8 float-search">
                <input class="search-style" placeholder="Search (not yet implemented)">
            </form>
            <c:if test="${user != null}">
                <div class="pure-u-md-1-8 pure-u-sm-1-8 logout">
                    <form method="post" action="/logout">
                        <input title="Logout" type="image" src="logout-32.ico" alt="Submit"/>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</header>
</body>
</html>
