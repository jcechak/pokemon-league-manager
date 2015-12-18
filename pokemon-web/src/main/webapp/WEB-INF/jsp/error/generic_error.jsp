<%--
  Created by IntelliJ IDEA.
  User: Marek Sabo
  Date: 18.12.2015
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trainer</title>
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
    <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
</head>

<body>
<jsp:include page="../navigator.jsp"/>
<br>
<h2>Oops, we have a problem!</h2>
<c:if test="${not empty exceptionName }">
    <h3>It is actually ${exceptionName}</h3>
</c:if>

<c:if test="${not empty errorMessage}">
    <h4>Which means: ${errorMessage}</h4>
</c:if>

</body>

</html>