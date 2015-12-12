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
    </head>
    <body>
        <h1>Create new badge</h1>
        <form:form method="post" action="${pageContext.request.contextPath}/menu/badge/create"
                   modelAttribute="newBadge">
            <div>
                <form:label path="stadiumId" >Stadium</form:label>
                    <div>
                    <form:select path="stadiumId">
                        <c:forEach items="${stadiums}" var="s">
                            <form:option value="${s.id}">${s.city}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>

            <div>
                <form:label path="trainerId" >Trainer</form:label>
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
    </body>
</html>
