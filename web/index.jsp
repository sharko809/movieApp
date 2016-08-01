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
        <div>
            <form method="post" action="/login">
                <label for="userName">Email: </label>
                <input id="userName" type="text" name="userLogin"/><br/>
                <label for="userPassword">Password: </label>
                <input id="userPassword" type="password" name="userPassword"/><br/>
                <input type="submit" value="Login"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>