<%-- 
    Document   : menu
    Created on : 10.12.2015, 17:48:13
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager Menu</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/site.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <jsp:include page="../navigator.jsp"></jsp:include>
        <div class="myDiv" align="center" style='position:absolute'>
        <table style="width:40%; text-align:center">
            <tr>
                <td style="width:50%">
                    <div>
                        <a href="trainer/trainerList">
                            <img src="../resources/images/menu/trainers.jpg" alt="trainers" style="max-width:100%; max-height:100%" class="menuImage">
                        </a>
                    </div>
                </td>
                <td style="width:50%">
                    <a href="badge/badgeList">
                        <img src="../resources/images/menu/badges.png" alt="badges" style="max-width:100%; max-height:100%">
                    </a>
                </td>
            </tr>
            <tr>
                <td><p>Manage trainers</p></td>
                <td><p>Manage badges</td>
            </tr>
            <tr>
                <td style="width:50%">
                    <img src="../resources/images/menu/pokemons.jpg" alt="pokemons" style="max-width:100%; max-height:100%">
                </td>
                <td style="width:50%">
                    <img src="../resources/images/menu/stadiums.jpg" alt="stadiums" style="max-width:100%; max-height:100%">
                    
                </td>
            </tr>
            <tr>
                <td><p>Manage pokemons</p></td>
                <td><p>Manage stadiums</p></td>
            </tr>
        </table>
            </div>
    </body>
</html>
