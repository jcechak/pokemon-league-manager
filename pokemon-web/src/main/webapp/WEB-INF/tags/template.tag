<%-- 
    Document   : template
    Created on : 17.12.2015, 22:45:11
    Author     : Jaroslav Cechak
--%>

<%@tag description="basic template for other webpages" pageEncoding="UTF-8" dynamic-attributes="dynattrs"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="body" fragment="true" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><c:out value="${title}"/></title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </head>

    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}">Pokemon management</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Pokemon management<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/menu/pokemon/list">View all pokemons</a></li>
                                <li><a href="${pageContext.request.contextPath}/menu/pokemon/newform">Add new pokemon</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Trainer management<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/menu/trainer/list">View all trainers</a></li>
                                <li><a href="${pageContext.request.contextPath}/menu/trainer/new">Add new trainer</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Badge management<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/menu/badge/badgeList">View all badges</a></li>
                                <li><a href="${pageContext.request.contextPath}/menu/badge/new">Add new badge</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Stadium management<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/menu/stadium/stadiumList">View all stadiums</a></li>
                                <li><a href="${pageContext.request.contextPath}/menu/stadium/new">Add new stadium</a></li>
                            </ul>
                        </li>
                    </ul>                
                </div>
            </div>
        </nav>

        <div class="container">

            <!-- page title -->
            <c:if test="${not empty title}">
                <div class="page-header">
                    <h1><c:out value="${title}"/></h1>
                </div>
            </c:if>

            <!-- authenticated user info -->
            <!--
            <c:if test="${not empty authenticatedUser}">
                <div class="row">
                    <div class="col-xs-6 col-sm-8 col-md-9 col-lg-10"></div>
                    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
                        <div class="panel panel-default">
                            <div class="panel-body">
                <c:out value="${authenticatedUser.givenName} ${authenticatedUser.surname}"/>
            </div>
        </div>
    </div>
</div>
            </c:if>
            -->

            <!-- alerts -->
            <c:if test="${not empty alert_danger}">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <c:out value="${alert_danger}"/></div>
                </c:if>
                <c:if test="${not empty alert_info}">
                <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
            </c:if>
            <c:if test="${not empty alert_success}">
                <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
            </c:if>
            <c:if test="${not empty alert_warning}">
                <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
            </c:if>

            <!-- page body -->
            <jsp:invoke fragment="body"/>

            <!-- footer -->            
            <footer class="footer">
                <p>Pokemon managment by Java Team 6</p>
            </footer>
        </div>


    </body>
</html>
