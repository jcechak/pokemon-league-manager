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
    </head>
    <body>
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
                        <button class="editButton">Edit</button>
                    </td>
                    <td>
                        <button class="deleteButton">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>