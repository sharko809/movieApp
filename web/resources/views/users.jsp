<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dsharko
  Date: 8/10/2016
  Time: 11:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="/resources/css/vendor/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu.css">
    <!--<![endif]-->
    <link rel="stylesheet" href="/resources/css/mainPage.css">
    <link rel="stylesheet" href="/resources/css/admin.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-3-4  centered">
        <div class="pure-u-1">
            <table class="pure-table pure-table-bordered" style="width: 100%; margin: 10px auto 10px auto;">
                <thead>
                <tr>
                    <th>
                        <form style="margin: auto;" id="sortID" method="post" action="/admin/sortusers">
                            <input type="hidden" name="sortBy" value="id"/>
                            <input type="hidden" name="redirectFrom" value=""/>
                            <span id="id">ID</span>
                        </form>
                    </th>
                    <th>
                        <form style="margin: auto;" id="sortLogin" method="post" action="/admin/sortusers">
                            <input type="hidden" name="sortBy" value="login"/>
                            <input type="hidden" name="redirectFrom" value=""/>
                            <span id="login">Login</span>
                        </form>
                    </th>
                    <th>
                        <form style="margin: auto;" id="sortName" method="post" action="/admin/sortusers">
                            <input type="hidden" name="sortBy" value="userName"/>
                            <input type="hidden" name="redirectFrom" value=""/>
                            <span id="userName">Username</span>
                        </form>
                    </th>
                    <th>
                        <form style="margin: auto;" id="sortAdmin" method="post" action="/admin/sortusers">
                            <input type="hidden" name="sortBy" value="admin"/>
                            <input type="hidden" name="redirectFrom" value=""/>
                            <span id="admin">Admin</span>
                        </form>
                    </th>
                    <th>
                        <form style="margin: auto;" id="sortBanned" method="post" action="/admin/sortusers">
                            <input type="hidden" name="sortBy" value="banned"/>
                            <input type="hidden" name="redirectFrom" value=""/>
                            <span id="banned">Banned</span>
                        </form>
                    </th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${sortedUsers != null}">
                        <c:if test="${sortedUsers.size() >= 1}">
                            <c:forEach items="${sortedUsers}" var="user">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.login}</td>
                                    <td>${user.name}</td>
                                    <c:choose>
                                        <c:when test="${user.isAdmin()}">
                                            <td>
                                                <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            style="background-color: #7FFF00;"
                                                            title="Remove admin permissions"
                                                            type="submit">${user.isAdmin()}</button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width" title="Set admin"
                                                            type="submit">${user.isAdmin()}</button>
                                                </form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${user.isBanned()}">
                                            <td>
                                                <form method="post" action="/admin/ban" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            style="background-color: #FFCCCC;"
                                                            title="Unban user"
                                                            type="submit">${user.isBanned()}</button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="post" action="/admin/ban" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            title="Ban user"
                                                            type="submit">${user.isBanned()}</button>
                                                </form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${users != null}">
                                <c:if test="${users.size() >= 1}">
                                    <c:forEach items="${users}" var="user">
                                        <tr>
                                            <td>${user.id}</td>
                                            <td>${user.login}</td>
                                            <td>${user.name}</td>
                                            <c:choose>
                                                <c:when test="${user.isAdmin()}">
                                                    <td>
                                                        <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                            <input type="hidden" name="userID" value="${user.id}"/>
                                                            <input type="hidden" name="redirectFrom" value=""/>
                                                            <button class="pure-button max-width"
                                                                    style="background-color: #7FFF00;"
                                                                    title="Remove admin permissions"
                                                                    type="submit">${user.isAdmin()}</button>
                                                        </form>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                            <input type="hidden" name="userID" value="${user.id}"/>
                                                            <input type="hidden" name="redirectFrom" value=""/>
                                                            <button class="pure-button max-width" title="Set admin"
                                                                    type="submit">${user.isAdmin()}</button>
                                                        </form>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${user.isBanned()}">
                                                    <td>
                                                        <form method="post" action="/admin/ban" style="margin: 0px;">
                                                            <input type="hidden" name="userID" value="${user.id}"/>
                                                            <input type="hidden" name="redirectFrom" value=""/>
                                                            <button class="pure-button max-width"
                                                                    style="background-color: #FFCCCC;"
                                                                    title="Unban user"
                                                                    type="submit">${user.isBanned()}</button>
                                                        </form>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <form method="post" action="/admin/ban" style="margin: 0px;">
                                                            <input type="hidden" name="userID" value="${user.id}"/>
                                                            <input type="hidden" name="redirectFrom" value=""/>
                                                            <button class="pure-button max-width"
                                                                    title="Ban user"
                                                                    type="submit">${user.isBanned()}</button>
                                                        </form>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    No users found.
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
        <div class="pure-g">
            <div class="pure-u-3-4 centered">
                <div class="pure-g">
                    <div class="pure-u centered inline-flex">
                        <div class="page-number">
                            <c:if test="${currentPage != 1}">
                                <p>
                                    <a class="page-link" href="/admin/users?page=${currentPage - 1}">Prev</a>
                                </p>
                            </c:if>
                        </div>
                        <c:forEach begin="1" end="${numberOfPages}" var="i">
                            <div class="page-number">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <p class="page-current">${i}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>
                                            <a class="page-link" href="/admin/users?page=${i}">${i}</a>
                                        </p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                        <div class="page-number">
                            <c:if test="${currentPage ne numberOfPages}">
                                <p>
                                    <a class="page-link" href="/admin/users?page=${currentPage + 1}">Next</a>
                                </p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/resources/js/sort-users.js" type="text/javascript"></script>
</body>
</html>
