<%-- 
    Document   : navigator
    Created on : 12.12.2015, 16:24:24
    Author     : MiloS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/pa165/resources/css/navigator.css">
    </head>
    <body>
        <div id='cssmenu'>
            <ul>
                <li><a href='/pa165/menu/menu'><span>Home</span></a></li>
                <li class='active has-sub'><a href='#'><span>Manage</span></a>
                    <ul>
                        <li class='has-sub'><a href='/pa165/menu/trainer/list'><span>Trainers</span></a>
                        </li>
                        <li class='has-sub'><a href='/pa165/menu/badge/badgeList'><span>Badges</span></a>
                        </li>
                        <li class='has-sub'><a href='/pa165/menu/pokemon/list'><span>Pokemons</span></a>
                        </li>
                        <li class='has-sub'><a href='/pa165/menu/stadium/list'><span>Stadiums</span></a>
                        </li>
                    </ul>
                </li>
                <li><a href='#'><span>Loged as: ${userName}</span></a></li>
                <li class='last'><a href="<c:url value="/logout" />"><span>Logout</span></a></li>
            </ul>
        </div>
    </body>
</html>
