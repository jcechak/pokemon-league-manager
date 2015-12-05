<%-- 
    Document   : login
    Created on : 4.12.2015, 14:52:56
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>

<div class="login-card">
<br><br>
<form action="j_security_check" method=post>
    <input type="text" name="j_username" size="25" placeholder="Username">
    <input type="password" size="15" name="j_password" placeholder="Password">
    <input type="submit" name="login" class="login login-submit" value="Log in">
</form>
</div>
</html>
