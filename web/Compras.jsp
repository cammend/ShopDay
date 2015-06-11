<%-- 
    Document   : Compras
    Created on : 9/06/2015, 08:41:55 PM
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
<style type="text/css">
        #apDiv2 {
	position: absolute;
        overflow: auto;
	width:auto;
	height:auto;
	z-index:2;
	left: 0;
        right: 0;
	top: 65px;
}
</style>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=0.8, maximum-scale=0.8, minimum-scale=0.8">
        <link rel="stylesheet" href="css/base-l.css">
    
        
    </head>
    <body>
        <div id="cuerpo">
        <div id="logo">
            <h1><a>Lista Compra</a></h1>
        </div>
        <div class="botones" id="apDiv2" >
        <%
       String lista = "";    
       if(request.getParameter("lista")==null){
       lista = "0000";
       }
       else
       {
       lista=request.getParameter("lista"); //colocar en esta variable el codigo de la lista actual trabajada 
       }
       //colocar en esta variable el codigo de la lista actual trabajada  
       out.print("<form action=\"detabarrote.jsp\" method=\"post\">");
       out.print("<input type=\"hidden\" name=\"pagina\" value=\"2\">");
       out.print("<input type=\"hidden\" name=\"update\" value=\"90\">");
       out.print("<input type=\"hidden\" name=\"lista\" value="+lista+">");
       out.print("<input class=\"botones\" type=\"submit\" name=\"Nuevo Producto\" value=\"Nuevo Producto\"></br>");
       out.print("</form>");
       out.print("<form action=\"otraslistas.jsp\" method=\"post\">");
       
       out.print("<input class=\"botones\" type=\"submit\" name=\"Agregar Lista\" value=\"Agregar Lista\"></br>");
       out.print("</form>");     
        %>
            <table border="10"  style="margin: 0 auto">
                <tr>
                    <th>Producto</th>
                    <th>Cantidad Plan</th>
                    <th>Cantidad Com</th>
                    <th>Medida</th>
                    <th>Categoria</th>   
                </tr>
                <%
                if(request.getParameter("id")==null){}
                else{
                String id = request.getParameter("id");
                String can= request.getParameter("cantidadcom");
                Statement q4 = null;
                        q4 = ConexionDB.getConnection().createStatement();
                        q4.executeUpdate("UPDATE detallelista SET abastecido=1, cantidadcompra="+can+" WHERE idabarrote="+id); 
                }
                if(request.getParameter("id2")==null){}
                else{
                String id2 = request.getParameter("id2");
                Statement q6 = null;
                        q6 = ConexionDB.getConnection().createStatement();
                        q6.executeUpdate("DELETE FROM detallelista WHERE idabarrote="+id2+"");  
                }
                %>
                
                
                    <%
                    Statement q = null;
            ResultSet rs = null;
            
            q = ConexionDB.getConnection().createStatement();
            rs = q.executeQuery("SELECT abarrote.idabarrote, abarrote.descripcionabarrote, detallelista.cantidadplan, unidadmedida.Descripcion, categoria.descripcion FROM abarrote,detallelista,categoria,unidadmedida WHERE detallelista.idlista="+lista+" AND detallelista.idabarrote=abarrote.idabarrote AND abarrote.idcategoria=categoria.idcategoria AND abarrote.idunidadmedida=unidadmedida.idunidadMedida AND detallelista.abastecido=0 ");
            while(rs.next())
            {
            out.print("<form action=\"Compras.jsp\" method=\"post\"> ");
            out.print("<input type=\"hidden\" name=\"id\" value="+rs.getString("abarrote.idabarrote")+">");
            out.print("<tr><td>"+rs.getString("abarrote.descripcionabarrote")+"</td>");
            out.print("<td>"+rs.getString("detallelista.cantidadplan")+"</td>");
            out.print("<td><input type=\"text\" name=\"cantidadcom\" size=\"3\" value="+rs.getString("detallelista.cantidadplan")+"></td>");
            out.print("<td>"+rs.getString("unidadmedida.Descripcion")+"</td>");
            out.print("<td>"+rs.getString("categoria.descripcion")+"</td>");
            out.print("<input type=\"hidden\" name=\"lista\" value="+lista+">");
            out.print("<td><input type=\"image\" name=\"enviar3\" src=\"img/listo.png\"></td>");
            out.print("</form>");
            out.print("<form action=\"Compras.jsp\" method=\"post\"> ");
            out.print("<input type=\"hidden\" name=\"id2\" value="+rs.getString("abarrote.idabarrote")+">");
            out.print("<td><input type=\"image\" name=\"enviar4\" src=\"img/eliminar.jpg\"></td></tr>");
            out.print("</form>");
            
            }
                    %>  
                    
                    
            
                
                
                
            </table>    
            
         
        </div>
        </div>
    </body>
</html>
