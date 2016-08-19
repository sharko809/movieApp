<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/1/2016
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ooops...</title>
    <link rel="stylesheet" href="/resources/css/vendor/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-min.css">
    <link rel="stylesheet" href="/resources/css/error.css">
</head>
<body>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered marged-top">
        <div class="center-content">
            <img height="500" width="650" align="middle" src="/resources/images/error.jpg"/>
        </div>
        <div class="center-content">
            <h4>Something went wrong. Try something else...</h4>
        </div>
        <div class="center-content">
            <c:forEach items="${errorDetails}" var="errorField">
                <span>${errorField}</span><br>
            </c:forEach>
        </div>
        <div class="center-content">
            <h3>
                <a href="/home">Go home...</a>
            </h3>
        </div>
    </div>
</div>
</body>
</html>
