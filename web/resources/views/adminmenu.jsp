<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/9/2016
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin menu</title>
    <%--<link rel="stylesheet" href="/resources/css/pure/pure-min.css">--%>
    <%--<link rel="stylesheet" href="/resources/css/pure/base-min.css">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <%--<!--[if lte IE 8]>--%>
    <%--<link rel="stylesheet" href="/resources/css/pure/grids-responsive-old-ie-min.css">--%>
    <%--<link rel="stylesheet" href="/resources/css/pure/layouts/side-menu-old-ie.css">--%>
    <%--<![endif]-->--%>
    <%--<!--[if gt IE 8]><!-->--%>
    <%--<link rel="stylesheet" href="/resources/css/pure/grids-responsive-min.css">--%>
    <%--<link rel="stylesheet" href="/resources/css/pure/layouts/side-menu.css">--%>
    <%--<!--<![endif]-->--%>
    <%--<link rel="stylesheet" href="/resources/css/admin.css">--%>
</head>
<body>
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
<script src="/resources/js/pure/ui.js" type="text/javascript"></script>
</body>
</html>
