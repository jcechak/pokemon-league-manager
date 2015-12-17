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
        <title>Badge</title>
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"/>
        <c:set var="stadiumsMap" value="${stadiumsMap}"/>
        <h1>Trainer detail</h1>
        <table class="CSSTableGenerator">
        <thead>
            <tr>
                <th colspan="5">Trainer</th>

                <th>
                    <a href="delete">
                        <button class="deleteButton">Delete Trainer</button>
                    </a>
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
                        <th colspan="5">Owned pokemons</th>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <th>Nickname</th>
                        <th>Type</th>
                        <th>Skill level</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${trainer.pokemons}" var="pokemon">
                        <tr>
                            <td><c:out value="${pokemon.name}"/></td>
                            <td><c:out value="${pokemon.nickname}"/></td>
                            <td><c:out value="${pokemon.type}"/></td>
                            <td><c:out value="${pokemon.skillLevel}"/></td>
                            <td></td>
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
                        <th colspan="4">Awarded badges at stadiums</th>
                    </tr>
                    <tr>
                        <th>Stadium ID</th>
                        <th>Stadium city</th>
                        <th>Stadium type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${trainer.badges}" var="badge">
                        <tr>
                            <td><c:out value="${badge.stadiumId}"/></td>
                            <c:set var="stadium" value="${stadiumsMap[badge.id]}"/>
                            <td><c:out value="${stadium.city}"/></td>
                            <td><c:out value="${stadium.type}"/></td>
                            <td></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>
