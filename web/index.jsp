<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movies list</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="resources/css/mainPage.css">
    <link rel="stylesheet" type="text/css" href="resources/css/authPage.css">
    <link rel="stylesheet" type="text/css" href="resources/css/authHeader.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="resources/js/reset-variables.js" type="text/javascript"></script>
</head>
<body class="body-style">
<jsp:include page="/resources/views/header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <div id="auth-block" class="pure-u-1 inline-flex">
            <div class="pure-u-md-1-2 pure-u-sm-1">
                <form class="pure-form pure-form-aligned" method="post" action="/login">
                    <fieldset>
                        <div class="pure-control-group">
                            <label for="userName">E-mail: </label>
                            <input id="userName" type="text" name="userLogin" minlength="3" maxlength="60" placeholder="E-mail" required/>
                        </div>
                        <div class="pure-control-group">
                            <label for="userPassword">Password: </label>
                            <input id="userPassword" type="password" name="userPassword" minlength="3" maxlength="15" placeholder="Password" required/>
                        </div>
                        <div class="pure-controls">
                            <input class="pure-button" type="submit" value="Login"/>
                        </div>
                        <input type="hidden" name="regPage" value="regPage"/>
                        <c:if test="${!logUser.login.isEmpty()}">
                            <script type="text/javascript">
                                setLoginInputs('${logUser.login}');
                            </script>
                        </c:if>
                    </fieldset>
                </form>
            </div>
            <div class="pure-u-md-1-2 pure-u-sm-1">
                <form class="pure-form pure-form-aligned" method="post" action="/registration">
                    <fieldset>
                        <div class="pure-control-group">
                            <label for="newUserName">Nickname: </label>
                            <input id="newUserName" type="text" name="newUserName" minlength="1" maxlength="20" placeholder="Name to display" required/>
                        </div>
                        <div class="pure-control-group">
                            <label for="newUserLogin">Your email:</label>
                            <input id="newUserLogin" type="email" name="newUserLogin" minlength="3" maxlength="60" placeholder="E-mail" required/>
                        </div>
                        <div class="pure-control-group">
                            <label for="newUserPassword">Enter password: </label>
                            <input id="newUserPassword" type="password" name="newUserPassword" minlength="3" maxlength="15" placeholder="Password" required/>
                        </div>
                        <div class="pure-controls">
                            <input class="pure-button" type="submit" value="Register">
                        </div>
                    </fieldset>
                    <c:if test="${!regUser.name.isEmpty()}">
                        <script type="text/javascript">
                            setRegistrationInputs('${regUser.name}', '${regUser.login}');
                        </script>
                    </c:if>
                </form>
            </div>
        </div>
        <div class="pure-u-1">
            <c:if test="${result != null}">
                <div id="error-info" class="error-info">
                    <c:forEach items="${result}" var="r">
                        <p>${r}</p>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <div class="pure-u-1">
            <div class="info-block">
                <p>
                    To post reviews and rate movies you need to sign in.
                </p>
                <p>
                    You don't need to register to see movies list, watch trailers and read description.
                </p>
                <p>
                    <a id="home-link" href="/home">
                        Click this message to proceed to movies list without registration.
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>