<%--
  Created by IntelliJ IDEA.
  User: Marek Sabo
  Date: 15.12.2015
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trainer manager</title>
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
    <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
</head>
<body>
<jsp:include page="../../navigator.jsp"/>
<br>
<h1>Trainers</h1>
<c:set var="trainers" value="${trainers}"/>

<table class="CSSTableGenerator">
    <thead>
    <tr>
        <th colspan="7">Trainer list</th>

        <th>
            <a href="new">
                <button class="addButton">Add Trainer</button>
            </a>
        </th>
    </tr>
    <tr>
        <th>Trainer ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Date of birth</th>
        <th>Leader of stadium</th>
        <th></th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${trainers}" var="trainers">
        <tr>
            <td><c:out value="${trainers.id}"/></td>
            <td><c:out value="${trainers.name}"/></td>
            <td><c:out value="${trainers.surname}"/></td>
            <fmt:formatDate value="${trainers.dateOfBirth}" var="formattedDate" type="date" pattern="dd.MM.yyyy" />
            <td><c:out value="${formattedDate}"/></td>
            <td><c:out value="${trainers.stadium.city}"/></td>
            <td>
                <button class="editButton" onclick="location = 'view/${trainers.id}'">View</button>
            </td>
            <td>
                <button class="editButton">Edit</button>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/menu/trainer/delete/${trainer.id}">
                    <button type="submit" class="deleteButton">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>