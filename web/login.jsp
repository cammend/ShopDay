<%-- 
    Document   : login
    Created on : 30/04/2015, 02:42:55 PM
    Author     : cammend
--%>

<%@page import="clases.util.Shop"%>
<%@page import="clases.util.Archivo"%>
<%@page import="clases.Sesion"%>
<%@page import="clases.Redirect"%>
<%@page import="clases.Error"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
Sesion.init(request); //inicializar la sesion
if(Sesion.haySesion()){
	Redirect.irA("inicio.html", request, response);
}
int codError = Sesion.getError();
%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Shop Day - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="css/base.css">
<script type="text/javascript" src="js/validacion.js"></script>
<script type="text/javascript">
function cargar(){
	document.getElementById('nombre').focus();
<%
if( Sesion.hayDatosForm() ){
	String datoForm= Sesion.getAttr(Sesion.ATTR_DATOS_FORM);
	if(datoForm!=null){
		String imp = "document.getElementById(\"nombre\").value=\""+datoForm+"\"";
		out.println(imp);		
		if(codError == Error.ALIAS_CORREO_N_EXISTE){
		    out.println("document.getElementById('nombre').focus();");
		}else if(codError == Error.PASSWORD_N_COINCIDEN){
		    out.println("document.getElementById('pass').focus();");
		}
	}
}
%>
}
</script>
<style>
  #error{
      background: rgba(255,0,0,0.7);
      border-radius: 5px;
      float: left;
      <%
        if(codError == Error.OK || codError == -1){
            out.println("display: none;");
        }else{
            out.println("display: block;");
        }
      %>
      width: 100%;
      margin-top: 15px;
      padding: 15px 0px 15px 0px;
      text-align: center;
  }
  #error p{
      color: white;
  }
</style>
</head>
<body onload="cargar()">
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
    
    <%
  out.println("<div id=\"error\">");
  if(codError == Error.ALIAS_CORREO_N_EXISTE){
      out.println("<p>El correo electrónico o alias ingresado no existen.</p>");
  }else if(codError == Error.PASSWORD_N_COINCIDEN){
      out.println("<p>La contraseña es incorrecta.</p>");
  }
  out.println("</div>");
  %>

    <div id="div-inicia-sesion">
        <p><strong>Inicio de Sesión</strong></p>
        <form name="sesion" method="post" action="comprobar-login">
            <label>Nombre de Usuario</label>
            <input type="text" name="nombre" id="nombre" placeholder="Correo electrónico o alias" onblur="validarCorreo('nombre')" required>
            <label>Contraseña</label>
            <input type="password" name="pass" id="pass" placeholder="Contraseña" required>
            <input type="submit" value="Entrar">
        </form>
    </div>
  
    <div id="pie">
      <p>Shoping Day! - cammend</p>
    </div>
	
</div>
</body>
</html>

<% Sesion.limpiar(); %>
