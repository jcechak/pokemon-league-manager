<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stadium Manager</title>
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"></jsp:include>
        <br>
        <h1>Stadiums</h1>
        <c:set var="stadiums" value="${stadiums}"></c:set>
        <c:set var="trainersMap" value="${trainersMap}"></c:set>

        <table class="CSSTableGenerator">
            <thead>
                <tr>
                    <th colspan="5">List of stadiums</th>
                    <th>
                        <a href="new">
                            <button class="addButton">Add Stadium</button>
                        </a>
                    </th>
                </tr>
                <tr>
                    <th>Stadium ID </th>
                    <th>City </th>
                    <th>Type </th>
                    <th>Leader ID </th>
                    <th></th>


                </tr>
            </thead>
            <tbody>
            <c:forEach items="${stadiums}" var="stadium">
                <tr>
                    <td><c:out value="${stadium.id}"/> </td>
                    <td><c:out value="${stadium.city}"/> </td>
                    <td><c:out value="${stadium.type}"/> </td>
                    <td><c:out value="${stadium.stadiumLeaderId}"/> </td>
                    <c:set var="trainer" value="${trainersMap[stadium.id]}"/>
                    <td><button class="editButton" onclick="location = 'editStadium/${stadium.id}'">Edit</button>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/menu/stadium/delete/${stadium.id}">
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