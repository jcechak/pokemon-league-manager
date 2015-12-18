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
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"></jsp:include>
        <br>
        <h1>Badges</h1>
        <c:set var="badges" value="${badges}"></c:set>
        <c:set var="stadiumsMap" value="${stadiumsMap}"></c:set>

    <table class="CSSTableGenerator">
        <thead>
            <tr>
                <th colspan="4">Badges list</th>
                
                <th>
                    <a href="new">
                        <button class="addButton">Add Badge</button>
                    </a>
                </th>
            </tr>
            <tr>
                <th>Badge ID</th>
                <th>Stadium ID</th>
                <th>Stadium name</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${badges}" var="badge">
                <tr>
                    <td><c:out value="${badge.id}"/></td>
                    <td><c:out value="${badge.stadiumId}"/></td>
                    <c:set var="stadium" value="${stadiumsMap[badge.id]}"/>
                    <td><c:out value="${stadium.city}"/></td>
                    <td>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/menu/badge/delete/${badge.id}">
                            <button type="submit" class="deleteButton">Delete</button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
