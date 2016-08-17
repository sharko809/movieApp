<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/3/2016
  Time: 12:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin panel</title>
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
                <li class="menu-item"><a href="/admin/users" class="pure-menu-link">Users</a></li>
                <li class="menu-item"><a href="/admin/newuser" class="pure-menu-link">Create user</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="pure-g custom-margin">
    <div class="pure-u-3-4 centered">
        <div class="pure-u-1">
            <div class="pure-g"></div>
            <div class="pure-u-7-8 pure-u-sm-1 centered" style="margin-top: 20px;">
                <div id="welcome-block">
                    <p>Welcome to your admin panel, ${user.name}.</p>
                    <p>Here you are able to manage all site activities. Adding and deleting movies, banning users
                        and other functions are present here. Be wise admin!</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/resources/js/pure/ui.js" type="text/javascript"></script>
</body>
</html>
