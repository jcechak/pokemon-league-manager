<%-- 
    Document   : index
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
                <td><jsp:include page="login.jsp"></jsp:include></td>
            </tr>
        </table>
            <a href="menu/menu">Temporal acces to menu</a>
    </div>
</body>
</html>
