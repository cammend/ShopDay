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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><% out.print(titulo);%></title>
  </head>
  <body>
    <%
    %>
    <h1>Hello World!</h1>
    <a href="logout.html">Salir</a>
  </body>
</html>
