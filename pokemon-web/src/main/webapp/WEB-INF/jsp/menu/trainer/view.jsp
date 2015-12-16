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
        <jsp:include page="../../navigator.jsp"></jsp:include>
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
    </body>
</html>
