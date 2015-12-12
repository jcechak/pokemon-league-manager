<%-- 
    Document   : view
    Created on : 12.12.2015, 15:32:06
    Author     : MiloS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <h1>Badge detail</h1>
        <table class="CSSTableGenerator">
        <thead>
            <tr>
                <th colspan="3">Badge</th>
                
                <th>
                    <a href="delete">
                        <button class="deleteButton">Delete Badge</button>
                    </a>
                </th>
            </tr>
            <tr>
                <th>Badge ID</th>
                <th>Stadium ID</th>
                <th>Stadium name</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
                <tr>
                    <td><c:out value="${badge.id}"/></td>
                    <td><c:out value="${stadium.id}"/></td>
                    <td><c:out value="${stadium.city}"/></td>
                    <td></td>
                </tr>
        </tbody>
    </table>
    </body>
</html>
