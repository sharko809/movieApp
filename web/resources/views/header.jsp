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
    <link rel="stylesheet" href="/resources/css/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/header.css">
    <script src="/resources/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/redirect-url.js" type="text/javascript"></script>
    <script src="/resources/js/header.js" type="text/javascript"></script>
</head>
<body>
<header class="pure-g header-menu fixed z-index">
    <div class="pure-u-md-4-5 pure-u-sm-1 centered inline-flex">
        <div class="pure-u-1 inline-flex">

            <c:choose>
                <c:when test="${user == null}">
                    <div id="login-form" class="pure-u-md-3-8 pure-u-sm-3-8 max-width">
                        <form class="pure-form inline-flex" method="post" action="/login"
                              style="margin: 5px auto 5px auto;">
                            <input class="pure-input-1-2 input-margin" type="text" name="userLogin" minlength="3" maxlength="60" placeholder="E-mail" required/><br/>
                            <input class="pure-input-1-2 input-margin" type="password" name="userPassword" minlength="3" maxlength="15" placeholder="Password" required/><br/>
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            <input type="image" src="/resources/icons/login-32.ico" alt="Submit"/>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="user-welcome" class="pure-u-md-3-8 pure-u-sm-3-8 max-width">
                        <div class="pure-u-1 inline-flex">
                            <p id="" class="pure-u-1-2 menu-text">
                                <span id="login-text">
                                    Logged in as
                                </span>
                                <a id="loggedInName" class="menu-text" href="/account?id=${user.id}">
                                    <c:if test="${user.isAdmin()}">
                                        <script type="text/javascript">
                                            setAdminColor();
                                        </script>
                                    </c:if>
                                        ${user.name}
                                </a>
                            </p>
                            <c:if test="${user.isAdmin()}">
                                <a class="pure-u-1-2 menu-text" href="/admin" style="text-align: center;">
                                    <p>
                                        Admin panel
                                    </p>
                                </a>
                            </c:if>
                        </div>
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
                <a class="menu-text" href="/home">
                    <p>
                        Movie list
                    </p>
                </a>
            </div>

            <form class="pure-u-md-2-8 pure-u-sm-2-8 float-search" method="get" action="/search">
                <input id="searchInput" name="searchInput" type="text" class="search-style" maxlength="30" placeholder="Search movie">
            </form>
            <c:if test="${user != null}">
                <div class="pure-u-md-1-8 pure-u-sm-1-8 logout">
                    <form method="post" action="/logout">
                        <input title="Logout" type="image" src="/resources/icons/logout-32.ico" alt="Submit"/>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</header>
</body>
</html>
