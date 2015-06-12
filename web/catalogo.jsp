<%-- 
    Document   : catalogo
    Created on : 3/06/2015, 06:27:17 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <title>JSP Page</title>
    <link href="css/base.css" rel="stylesheet" type="text/css">
    <link href="css/base-l.css" rel="stylesheet" type="text/css">
    <link href="css/form.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    body,td,th {
	font-size: 10px;
	text-align: center;
}
    #apDiv1 {
	position: absolute;
	width:auto;
	height:auto;
	margin: 0 auto;
	z-index:1;
	left: 95px;
	top: 24px;
	font-size: 18px;
}
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

    #apDiv3 {	position:absolute;
	width:353px;
	height:22px;
	z-index:1;
	left: 0px;
	top: 4px;
	font-size: 18px;
}
    </style>
    </head>
    <body>
        <div id="logo">MI LISTA PREFERIDA</div>
    
    <div class="principal" id="apDiv2">    
        <form action="detabarrote.jsp" method="post">
            <input type="hidden" name="pagina" value="1">
            <input type="hidden" name="update" value="0">
            <input class="botones" type="submit" name="Nuevo Producto" value="Nuevo Producto"></br>
        </form>
        
        <table border="10"  style="margin: 0 auto">
            
            <tr><td colspan="6"><b><big>Lista Catalogo</big></b></td></tr>
            <tr>
                <th><b><big>Producto</big></b></th>
                <th><b><big>Cantidad</big></b></th>
                <th><b><big>Unidad de medida</big></b></th>
                <th><b><big>Categoria</big></b></th>
                <th><b><big>Eliminar</big></b></th>
                <th><b><big>Editar</big></b></th>
            </tr>
            
            
             
           
                        
                        <%
                         String id = request.getParameter("productoeli");
                        if(id!=null){
                        Statement q6 = null;
                        q6 = ConexionDB.getConnection().createStatement();
                        q6.executeUpdate("DELETE FROM detallelista WHERE idabarrote="+id+"");   
                         
                        Statement q5 = null;
                        q5 = ConexionDB.getConnection().createStatement();
                        q5.executeUpdate("DELETE FROM abarrote WHERE idabarrote="+id+"");
                                     }
                        %>
            
            <%
            Statement q = null;
            ResultSet rs = null;
            
            q = ConexionDB.getConnection().createStatement();
            rs = q.executeQuery("SELECT abarrote.idabarrote, abarrote.descripcionabarrote,abarrote.cantidadplan,categoria.descripcion, unidadmedida.Descripcion FROM abarrote, categoria, unidadmedida WHERE idusuario='"+Sesion.getAliasUsuario()+"' AND abarrote.idcategoria=categoria.idcategoria AND abarrote.idunidadmedida=unidadmedida.idunidadMedida ");
            while(rs.next())
            {
            out.print("<form action=\"catalogo.jsp\" method=\"post\"> ");
            out.print("<td><h2>"+rs.getString("abarrote.descripcionabarrote")+"</h2></td>");
            out.print("<td><h2>"+rs.getString("abarrote.cantidadplan")+"</h2</td>");
            out.print("<td><h2>"+rs.getString("categoria.descripcion")+"</h2>");
            out.print("<td><h2>"+rs.getString("unidadmedida.Descripcion")+"</h2></td>");
            out.print("<input type=\"hidden\" name=\"productoeli\" value="+rs.getString("abarrote.idabarrote")+">");
            out.print("<td><input type=\"image\" name=\"enviar2\" src=\"img/eliminar.jpg\"></td>");
            out.print("</form>");
            out.print("<form action=\"detabarrote.jsp\" method=\"post\"> ");
            out.print("<input type=\"hidden\" name=\"pagina\" value=\"1\">");
            out.print("<input type=\"hidden\" name=\"productoeli\" value="+rs.getString("abarrote.idabarrote")+">");
            out.print("<input type=\"hidden\" name=\"update\" value=\"45\">");
            out.print("<td><input type=\"image\" name=\"enviar3\" src=\"img/editar.png\"></td></tr>");
            out.print("</form>");
            }
            %>
            
            
            
        </table>
        
        

        
   
      <a href="preparar-lista.html"><img src ="img/regresar.png"></a>             
                    
        
                        
    </div>

    
    </body>
</html>
