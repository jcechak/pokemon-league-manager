<%--
  Created by IntelliJ IDEA.
  User: Marek Sabo
  Date: 15.12.2015
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

<c:if test="${alert_success != null}">
    <div id="status_message" style="color:green">${alert_success}</div>
</c:if>

<c:set var="trainers" value="${trainers}"/>

<table class="CSSTableGenerator">
    <thead>
    <tr>
        <th colspan="9">Trainer list</th>

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
        <th>Badges count</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${trainers}" var="trainer">
        <tr>
            <td><c:out value="${trainer.id}"/></td>
            <td><c:out value="${trainer.name}"/></td>
            <td><c:out value="${trainer.surname}"/></td>
            <fmt:formatDate value="${trainer.dateOfBirth}" var="formattedDate" type="date" pattern="dd.MM.yyyy" />
            <td><c:out value="${formattedDate}"/></td>
            <td><c:out value="${trainer.stadium.city}"/></td>
            <td><c:out value="${badgesCount[trainer.id]}"/></td>
            <td>
                <c:if test="${!availableStadiums[trainer.id].isEmpty()}">

                    <select id="stadiumId" name="stadiumId" >
                        <c:forEach items="${availableStadiums[trainer.id]}" var="s">
                            <option value="${s.id}">${s.city}</option>
                        </c:forEach>
                    </select>
                    <button class="addButton" onclick="location =
                                '${pageContext.request.contextPath}/menu/badge/new'">Assign badge</button>
                </c:if>
            </td>
            <td>
                <button class="editButton" onclick="location = 'view/${trainer.id}'">View</button>
            </td>
            <td>
                <button class="editButton" onclick="location = 'edit/${trainer.id}'">Edit</button>
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
