<%--
  Created by IntelliJ IDEA.
  User: Marek Sabo
  Date: 15.12.2015
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trainer</title>
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
    <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
</head>
<body>
<jsp:include page="../../navigator.jsp"/>
<br>
<h1>Trainer detail</h1>
<c:set var="stadiumsMap" value="${stadiumsMap}"/>
<div id="status_message" style="color:green">${alert_success}</div>
<div id="status_message" style="color:red">${alert_error}</div>
<table class="CSSTableGenerator">
    <thead>
    <tr>
        <th colspan="5">Trainer</th>

        <th>
            <form method="post" action="${pageContext.request.contextPath}/menu/trainer/delete/${trainer.id}">
                <button type="submit" class="deleteButton">Delete</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </th>
    </tr>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Date of birth</th>
        <th>Leader of stadium</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${trainer.id}"/></td>
        <td><c:out value="${trainer.name}"/></td>
        <td><c:out value="${trainer.surname}"/></td>
        <fmt:formatDate value="${trainer.dateOfBirth}" var="formattedDate" type="date" pattern="dd.MM.yyyy" />
        <td><c:out value="${formattedDate}"/></td>
        <td><c:out value="${trainer.stadium.city}"/></td>
        <td></td>
    </tr>
    </tbody>
</table>

<div class="row">
    <div class="col-xs-6">
        <table class="table">
            <thead>
            <tr>
                <th colspan="4">Owned pokemons</th>
                <th>
                    <button class="addButton" onclick="location = '${pageContext.request.contextPath}/menu/pokemon/newform'">Add pokemon</button>
                </th>
            </tr>
            <tr>
                <th>Name</th>
                <th>Nickname</th>
                <th>Type</th>
                <th>Skill level</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${trainer.pokemons}" var="pokemon">
                <tr>
                    <td><c:out value="${pokemon.name}"/></td>
                    <td><c:out value="${pokemon.nickname}"/></td>
                    <td><c:out value="${pokemon.type}"/></td>
                    <td><c:out value="${pokemon.skillLevel}"/></td>
                    <td>

                        <button class="deleteButton"
                                onclick="location = '${pageContext.request.contextPath}/menu/pokemon/delete/${pokemon.id}'">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="row">
    <div class="col-xs-6">
        <table class="table">
            <thead>
            <tr>
                <th colspan="3">Awarded badges at stadiums</th>
                <th>
                    <button class="addButton" onclick="location = '${pageContext.request.contextPath}/menu/badge/new'">Add Badge</button>
                </th>
            </tr>
            <tr>
                <th>Stadium ID</th>
                <th>Stadium city</th>
                <th>Stadium type</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${trainer.badges}" var="badge">
                <tr>
                    <td><c:out value="${badge.stadiumId}"/></td>
                    <c:set var="stadium" value="${stadiumsMap[badge.id]}"/>
                    <td><c:out value="${stadium.city}"/></td>
                    <td><c:out value="${stadium.type}"/></td>
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
    </div>
</div>

</body>
</html>
