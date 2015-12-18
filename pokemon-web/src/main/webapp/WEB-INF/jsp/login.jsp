<%-- 
    Document   : login
    Created on : 5.12.2015, 16:22:40
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pokemon league</title>
        <link rel="stylesheet" type="text/css" href="resources/css/site.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <script src="resources/js/js.js"></script>
    </head>
    <body>
        <div align="center">
            <img src="resources/images/Heading.png" alt="head"><br>
            <img src="resources/images/Login.png" alt="head" height="100"><br>
            <table>
                <tr>
                    <td>
                        <div class="loginImage">
                            <img src="resources/images/giphy.gif" alt="cat" width="360">
                        </div>
                    </td>
                    <td>
                        <div class="login-card">
                             <c:url value="/login" var="loginUrl"/>
                             <form action="${loginUrl}" method="post">
                                <input type="text" id="username" name="username" size="25" placeholder="Username">
                                <input type="password" id="password" name="password" size="15" placeholder="Password">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="submit" name="login" class="login login-submit" value="Log in">
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
                                <strong style="color:red">PLZ do not open in Firefox</strong>
        </div>
    </body>
</html>
