<%-- 
    Document   : preparaLista
    Created on : 26/05/2015, 01:44:21 PM
    Author     : cammend
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="clases.Sesion"%>
<%@page import="clases.Redirect"%>
<%
Sesion.init(request); //inicializar la sesion
if( !Sesion.haySesion() ){
	Redirect.irA("login.html", request, response);
}
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <title>Preparar Lista</title>
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/form.css">
    </head>
    <body>
<div id="cuerpo">

    <div id="logo">
      <h1>Preparar Lista de Abarrotes</h1>
    </div>

    <div class="formulario">
        <form name="sesion" method="post" action="insertarLista">
            <label>Descripción</label>
            <input type="text" name="descripcion" id="descripcion" placeholder="" required>
            <label>Fecha</label>
            <input type="text" name="fecha" id="fecha" placeholder="" required>
            <label>Presupuesto</label>
            <input type="text" name="presupuesto" id="presupuesto" placeholder="" required>
            <input type="submit" value="Siguiente">
        </form>
    </div>
    
    <div id="pie">
      <p>Shoping Day! - cammend</p>
    </div>
	
</div>
    </body>
</html>
