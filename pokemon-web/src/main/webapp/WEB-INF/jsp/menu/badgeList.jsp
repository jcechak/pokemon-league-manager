<%-- 
    Document   : badgeList
    Created on : 11.12.2015, 13:33:17
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Badges manager</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:set var="badges" value="${badges}"></c:set>
        <c:forEach items="${badges}" var="badge">
            <p><c:out value="${badge.id}"/></p>
        </c:forEach>
        <c:forEach items="${stadiums}" var="stadium">
            <p><c:out value="${stadium.city}"/></p>
        </c:forEach>
    </body>
</html>
