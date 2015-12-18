<%-- 
    Document   : newBadge
    Created on : 12.12.2015, 13:24:21
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Badge</title>
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"></jsp:include>
            <h1>Create new badge</h1>
        <form:form method="post" action="${pageContext.request.contextPath}/menu/badge/create"
                   modelAttribute="newBadge">
            <div>
                <label>Stadium</label>
                <div>
                    <form:select path="stadiumId">
                        <c:forEach items="${stadiums}" var="s">
                            <form:option value="${s.id}">${s.city}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>

            <div>
                <label>Trainer</label>
                <div>
                    <form:select path="trainerId">
                        <c:forEach items="${trainers}" var="t">
                            <form:option value="${t.id}">${t.name} ${t.surname}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <button type="submit">Create badge</button>
        </form:form>
        <p style="color:red">${errorMessage}</p>
    </body>
</html>
