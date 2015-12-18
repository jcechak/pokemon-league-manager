<%--
    Document   : editStadium
    Created on : 18.12.2015,
    Author     : Dominika Talianova
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Stadium</title>
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"/>
        <h1>Edit stadium</h1>
        <form:form method="post" action="${pageContext.request.contextPath}/menu/stadium/update"
                                modelAttribute="stadium">
            <form:hidden path="id" value="${stadium.id}"/>
            <div class="form-group ${city_error?'has-error':''}">
                <form:label path="city">City </form:label>
                <div>
                    <form:input path="city" required="true"/>
                    <form:errors path="city"/>
                </div>
            </div>
            <div>
                <form:label path="stadiumLeaderId" >Select trainer ID </form:label>
                <div>
                    <select name="stadiumLeaderId">
                        <c:forEach items="${trainersWithoutStadium}" var="trainer">
                            <option value="${trainer.id}">${trainer.id}</option>
                        </c:forEach>
                    </select>
                </div>
                <form:label path="type" >Select Pokemon type </form:label>
                <div>
                    <select name="type">
                        <c:forEach items="${typeList}" var="oneType">
                            <option value="${oneType}">${oneType}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            </div>
            <button type="submit">Save changes</button>
        </form:form>
    </body>
</html>


