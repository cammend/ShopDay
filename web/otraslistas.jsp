<%-- 
    Document   : otraslistas
    Created on : 9/06/2015, 08:45:24 PM
    Author     : EdwinArturo
--%>
<%@page import="clases.DB.ConexionDB"%>
<%@page import="java.sql.*"%>
<%@page import="clases.Sesion"%>
<%@page import="clases.Redirect"%>
<%
Sesion.init(request); //inicializar la sesion
if( !Sesion.haySesion() ){
	Redirect.irA("login.html", request, response);
}
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=0.8, maximum-scale=0.8, minimum-scale=0.8">
        <link rel="stylesheet" href="css/base-l.css">
        <title>JSP Page</title>
    </head>
    <body>
       <div id="cuerpo">
        <div id="logo">
            <h1><a>Listas</a></h1>
        </div>
           <div id="botones" >
               <table border="10"  style="margin: 0 auto">
                   <tr>
                       <th>Lista</th>
                       <th>Fecha</th>
                       <th>Presupuesto</th>
                   </tr>
            <%
                    Statement q = null;
            ResultSet rs = null;
            
            q = ConexionDB.getConnection().createStatement();
            rs = q.executeQuery("SELECT lista.idlista, lista.descripcionlista, lista.fecha, lista.edad FROM lista WHERE lista.idusuario='"+Sesion.getAliasUsuario()+"' AND lista.estado=0 ORDER BY lista.fecha");
            while(rs.next())
            {
            out.print("<form action=\"Compras.jsp\" method=\"post\"> ");
            out.print("<tr><td>"+rs.getString("lista.descripcionlista")+"</td>");
            out.print("<td>"+rs.getString("lista.fecha")+"</td>");
            out.print("<td>"+rs.getString("lista.edad")+"</td>");
            out.print("<input type=\"hidden\" name=\"lista\" value="+rs.getString("lista.idlista")+">");
            out.print("<td><input type=\"image\" name=\"enviar3\" src=\"img/listo.png\"></td></tr>");
            out.print("</form>");
            }
                    %>  
               </table>
             </div>
        </div>
    </body>
</html>
