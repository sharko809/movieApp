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
</head>
<body>
<img height="500" width="650" align="middle" src="https://i.ytimg.com/vi/jnqvg43Jug4/maxresdefault.jpg"/>
<h4>Something went wrong. Try something else...</h4>
<c:forEach items="${errorDetails}" var="errorField">
    <span>${errorField}</span><br>
</c:forEach>
</body>
</html>
