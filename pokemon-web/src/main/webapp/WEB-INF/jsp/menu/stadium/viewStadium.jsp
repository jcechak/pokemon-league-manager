<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stadium</title>
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"></jsp:include>
        <h1>Stadium detail</h1>
        <c:set var="stadiums" value="${stadiums}"></c:set>
        <c:set var="trainersMap" value="${trainersMap}"></c:set>

        <table class="CSSTableGenerator">
        <thead>
            <tr>
                <th colspan="4">Stadium</th>
                <th>
                    <a href="delete">
                       <button class="deleteButton">Remove Stadium</button>
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
            <tr>
                <td><c:out value="${stadium.id}"/></td>
                <td><c:out value="${stadium.city}"/></td>
                <td><c:out value="${stadium.type}"/></td>
                <td><c:out value="${stadium.stadiumLeaderId}"/></td>
                <c:set var="trainer" value="${trainersMap[stadium.id]}"/>
                <td></td>
            </tr>
        </tbody>
    </body>
</html>


