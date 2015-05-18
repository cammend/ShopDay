<%-- 
    Document   : login
    Created on : 30/04/2015, 02:42:55 PM
    Author     : cammend
--%>

<%@page import="clases.util.Shop"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Shop Day - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="css/base.css">
</head>

<body>
<div id="cuerpo">

    <div id="logo">
      <h1>
        <%
          out.println("<a href=\""+Shop.getApp()+"/\">");
          out.println("Shop Day");
          out.println("</a>");        
        %>
        </h1>
    </div>

    <div id="div-inicia-sesion">
        <p><strong>Inicio de Sesión</strong></p>
        <form name="sesion" method="post" >
            <label>Nombre de Usuario</label>
            <input type="text" name="nombre" >
            <label>Contraseña</label>
            <input type="password" name="pass" >
            <input type="submit" value="Entrar">
        </form>
    </div>
  
    <div id="pie">
      <p>Shoping Day! - cammend</p>
    </div>
	
</div>
</body>
</html>

