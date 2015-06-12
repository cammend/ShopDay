<%-- 
    Document   : home
    Created on : 11/05/2015, 02:16:21 AM
    Author     : cammend
--%>

<%@page import="clases.Sesion"%>
<%@page import="clases.Redirect"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
Sesion.init(request);
String titulo = "ShopDay";
String alias;
if( !Sesion.haySesion() ){
	Redirect.irA("login.html",request,response);
}else{
	alias = Sesion.getAliasUsuario();
	if(alias!=null){
            titulo += " - "+alias;
	}
}
%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title><% out.print(titulo);%></title>
    <link rel="stylesheet" href="css/base-l.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script>
        function datosGuardados(){
            alert("Lista Guardada");
        }
        
        $(document).ready(function (){
            <%
            String consulta = Sesion.getAttr("Consulta");
            if( consulta!=null ){
                if( consulta.equals("OK") ){
                    Sesion.eliminarAttr("Consulta");
                    out.println("datosGuardados();");
                }
            }
            %>
        });
    </script>
  </head>
  
  <body>
    <div id="cuerpo">
        <div id="logo">
          <h1><a href="">Shop Day</a></h1>
        </div>
        <div id="links">

        </div>

        <div id="botones">
            <a href="nueva-lista.html">Preparar Lista de Abarrotes</a>
            <p>o</p>
            <a href="comprar-lista.html">Comprar mis Abarrotes</a>
        </div>
          <div class="botones">
              <a href="logout.html">Salir</a>
          </div>


        <div id="pie">
          <p>Shoping Day! - cammend</p>
        </div>
    </div>
  </body>
</html>
