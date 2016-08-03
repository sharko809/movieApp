<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/3/2016
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div class="pure-u-md-4-5 pure-u-sm-7-8 centered">
        <div class="pure-u-2-5 pure-u-sm-3-8" style="margin-top: 14px;">
            <span style="color: antiquewhite;">
                You have logged in as ${user.name}.
            </span>
        </div>
        <form class="pure-u-1-5 pure-u-sm-2-8 float-search">
            <input class="search-style" placeholder="Search (not yet implemented)">
        </form>
    </div>
</header>
</body>
</html>
