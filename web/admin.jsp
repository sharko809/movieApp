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
    <div class="pure-u">
        <a href="/admin/addmovie">Add movie</a>
    </div>
    <div class="pure-u">
        <a href="/admin/movielist">Movies list</a>
    </div>
</div>
</body>
</html>
