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
    <div class="pure-u-2-5 centered">
        <form class="pure-form" method="post" action="/admin/addmovie">
            <fieldset class="pure-group">
                <input name="title" type="text" class="pure-input-1-2" placeholder="Title" />
                <input name="director" type="text" class="pure-input-1-2" placeholder="Director" />
                <input name="releaseDate" type="date" class="pure-input-1-2" placeholder="Release date" />

            </fieldset>

            <fieldset class="pure-group">
                <input name="trailerUrl" type="url" class="pure-input-1-2" placeholder="Trailer URL" />
                <textarea name="description" class="pure-input-1-2" placeholder="Description"></textarea>
            </fieldset>
            <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Add movie</button>
        </form>
    </div>
    <div class="pure-u-1 centered">
        <c:forEach items="${result}" var="r">
            ${r}<br/>
        </c:forEach>
    </div>
</div>
</body>
</html>
