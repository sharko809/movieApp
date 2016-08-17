<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/10/2016
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account info</title>
    <link rel="stylesheet" href="/resources/css/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/pure/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css">
    <script src="/resources/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/redirect-url.js" type="text/javascript"></script>
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>

<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <div class="pure-u-1">
            <c:if test="${user != null}">
                <form class="pure-form pure-form-aligned" style="text-align: center; padding-right: 90px;" method="post"
                      action="/updaccount">
                    <fieldset>
                        <div class="pure-control-group">
                            <label for="userName">Username: </label>
                            <input type="text" id="userName" name="userName" value="${thisUser.name}" readonly/>
                            <button id="editName" type="button" class="pure-button">Edit</button>
                        </div>
                        <div class="pure-control-group">
                            <label for="userLogin">Login: </label>
                            <input type="email" id="userLogin" name="userLogin" value="${thisUser.login}" readonly/>
                            <button id="editLogin" type="button" class="pure-button">Edit</button>
                        </div>
                        <div class="pure-control-group">
                            <label for="userPassword">Password: </label>
                            <input type="password" id="userPassword" name="userPassword"
                                   title="If you want to change password - type new one here."
                                   placeholder="Leave blank to keep old password" readonly/>
                            <button id="editPassword" type="button" class="pure-button">Edit</button>
                        </div>
                        <div class="pure-controls">
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            <button type="submit" class="pure-button">Update account</button>
                        </div>
                    </fieldset>
                </form>
            </c:if>
        </div>
        <div class="pure-u-1">
            <c:if test="${result != null}">
                <c:forEach items="${result}" var="r">
                    <div id="empty-set">
                            ${r}<br/>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<script src="/resources/js/account-enable-input.js" type="text/javascript"></script>
</body>
</html>
