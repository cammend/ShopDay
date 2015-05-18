<%-- 
    Document   : debug
    Created on : 14/05/2015, 05:49:37 PM
    Author     : cammend
--%>

<%@page import="clases.util.Shop"%>
<%@page import="clases.util.Archivo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pagina de Depuración ShopDay</h1>
        <%
        String linea = null;
        try{
            while( (linea=Archivo.obtenerLinea("/home/cammend/shopday.log"))!=null ){
                out.println("<p>"+linea+"</p>");
            }
            Archivo.cerrar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        %>
    </body>
</html>
