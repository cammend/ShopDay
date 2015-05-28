<%-- 
    Document   : login
    Created on : 30/04/2015, 02:42:55 PM
    Author     : cammend
--%>

<%@page import="clases.util.Shop"%>
<%@page import="clases.Sesion"%>
<%@page import="clases.util.Archivo"%>
<%@page import="clases.Error"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
Sesion.init(request); //inicializando la sesion
int codError = Sesion.getError();
%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Shop Day - Registro</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<script type="text/javascript" src="js/validacion.js"></script>
<link rel="stylesheet" href="css/base.css">
<script type="text/javascript">
<%
if( Sesion.hayDatosForm() ){
	String datosForm[] = Sesion.getArrayAttr(Sesion.ATTR_DATOS_FORM);
	if(datosForm.length>0){
                out.println("document.getElementById('nombre').value='"+datosForm[0]+"'");
		out.println("document.getElementById('alias').value='"+datosForm[1]+"'");
		out.println("document.getElementById('edad').value='"+datosForm[2]+"'");
                if(codError == Error.CORREO){
		}else if(codError == Error.ALIAS){
		    out.println("document.getElementById('alias').value=''");
		    out.println("document.getElementById('alias').focus();");
		}else if(codError == Error.ALIAS_CORREO){
		}
	}
}
%>
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

<body>
<div id="cuerpo">

    <div id="logo">
        <h1>
            <%
            out.println("<a href=\""+Shop.getApp()+"\">Shop Day</a>");
            %>
        </h1>
    </div>
  <%
  out.println("<div id=\"error\">");
  if(codError == Error.CORREO){
      out.println("<p>El correo ingresado ya está en uso.</p>");
  }else if(codError == Error.ALIAS){
      out.println("<p>El alias ingresado ya está en uso.</p>");
  }else if(codError == Error.ALIAS_CORREO){
      out.println("<p>El correo y el alias ingresado ya están en uso.</p>");
  }else if(codError == Error.PASSWORD_N_COINCIDEN){
      out.println("<p>Las contraseñas ingresadas no coinciden.</p>");
  }
  out.println("</div>");
  %>
    <div id="div-inicia-sesion">
        <p><strong>Nuevo Usuario</strong></p>
        <form name="sesion" method="post" action="comprobar-registro">
            <label>Nombre Completo</label>
            <input type="text" name="nombre" id="nombre" placeholder="Escriba su nombre completo" required>
            <label>Nombre Usuario</label>
            <input type="text" name="alias" id="alias" placeholder="Escriba un alias o nickname" required>
            <label>Edad</label>
            <input type="text" name="edad" id="edad" placeholder="Escriba su edad" required>
            <label>Contraseña</label>
            <input type="password" name="pass" id="pass" placeholder="Escriba su contraseña" required>
            <label>Confirmar Contraseña</label>
            <input type="password" name="pass2" id="pass2" placeholder="Repetir contraseña" onblur="validarPassword('pass','pass2')" required>
            <input type="submit" value="Registrar">
        </form>
    </div>
  
    <div id="pie">
      <p>Shoping Day! - cammend</p>
    </div>
	
</div>
</body>
</html>

<%
Sesion.limpiar();
%>