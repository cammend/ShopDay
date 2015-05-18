<%-- 
    Document   : index
    Created on : 3/05/2015, 05:26:59 PM
    Author     : cammend
--%>
<%@page import="clases.Redirect"%>
<%@page import="clases.Sesion"%>
<%@page import="clases.util.Archivo"%>
<%@page import="clases.util.Shop"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String err = Archivo.guardarCadena("Inicio de aplicacion");
    String alias = Sesion.getAttr(request, Sesion.ATTR_NOMBRE_USUARIO);
    alias = null;
    if( alias != null ){
        Redirect.redireccionar("inicio.html", request, response);
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Shop Day - Inicio</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="css/base-l.css">
</head>

<body>
<div id="cuerpo">

    <div id="logo">
      <h1><a href="">Shop Day</a></h1>
        <p>
            <%
            //out.println(err); //depuracion
            %>
            ¡Listo para ir de compras!
        </p>
    </div>

    <div id="botones">
      <%
        out.println("<a href=\"login.html\">Iniciar Sesión</a>");
        out.print("<a href=\"registrarse.html\">Registrarse!</a>");
       %>
    </div>
    
    <div id="pie">
      <p>Shoping Day! - cammend</p>
    </div>
	
</div>
</body>
</html>