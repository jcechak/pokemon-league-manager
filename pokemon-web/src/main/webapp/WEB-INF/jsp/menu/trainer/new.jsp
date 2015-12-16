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
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
        <title>jQuery UI Datepicker - Default functionality</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script type="text/javascript">
            $(function() {
                $("#birthpicker" ).datepicker({
                    changeMonth: true,
                    changeYear: true,
                    yearRange: '1920:2015',
                    dateFormat : 'dd.mm.yy',
                    defaultDate: new Date(1993, 11, 9)
                });
            });
        </script>
    </head>
    <body>
    <jsp:include page="../../navigator.jsp"/>
    <h1>Create new trainer</h1>
    <form:form method="post" action="${pageContext.request.contextPath}/menu/trainer/create"
               modelAttribute="new">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name"/>
                <form:errors path="name"/>
            </div>
        </div>
        <div class="form-group ${surname_error?'has-error':''}">
            <form:label path="surname" >Surname</form:label>
            <div class="col-sm-10">
                <form:input path="surname"/>
                <form:errors path="surname"/>
            </div>
        </div>
        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" >Date of birth</form:label>
            <div class="col-sm-10">
                <form:input path="dateOfBirth" type="date" />
                <form:errors path="dateOfBirth"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create trainer</button>
    </form:form>
    </body>
</html>
