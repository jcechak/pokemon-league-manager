<%--
  Created by IntelliJ IDEA.
  User: Marek Sabo
  Date: 15.12.2015
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Trainer</title>
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
    <jsp:include page="../../navigator.jsp"/>
    <h1>Create new trainer</h1>

    <div id="status_message" style="color:green">${alert_success}</div>
    <div id="status_message" style="color:red">${alert_error}</div>

    <form:form method="post" action="${pageContext.request.contextPath}/menu/trainer/create"
               modelAttribute="new">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" required="true" />
                <form:errors path="name"/>
            </div>
        </div>
        <div class="form-group ${surname_error?'has-error':''}">
            <form:label path="surname" >Surname</form:label>
            <div class="col-sm-10">
                <form:input path="surname" required="true" />
                <form:errors path="surname"/>
            </div>
        </div>
        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" >Date of birth</form:label>
            <div class="col-sm-10">
                <form:input path="dateOfBirth" type="date" value="1993-12-08" required="true" />
                <form:errors path="dateOfBirth"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create trainer</button>
    </form:form>
    </body>
</html>
