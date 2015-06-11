<%-- 
    Document   : detabarrote
    Created on : 8/06/2015, 03:21:23 PM
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
	left: 58px;
	top: 24px;
	font-size: 18px;
}
    #apDiv2 {
	position:absolute;
	overflow: auto;
	width:auto;
	height:auto;
	z-index:2;
	left: 19px;
	right: auto;
	top: 46px;
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
    <div class="formulario" id="apDiv1">ABARROTE DETALLE</div>
    
            
             <div class="formulario" id="apDiv2">
       
             <form action="detabarrote.jsp" method="post"> 

            <%
            int pagina = 0;
            int update = 0;
            int productoup = 0;
            //String lista ="";
            if(request.getParameter("pagina")==null){
            pagina = 0;
            update = 0;
            productoup = 0;
            
            }
            else{
            pagina = Integer.parseInt(request.getParameter("pagina"));
            if(Integer.parseInt(request.getParameter("update"))==45){
            update = Integer.parseInt(request.getParameter("update"));
            productoup = Integer.parseInt(request.getParameter("productoeli"));}
            if(Integer.parseInt(request.getParameter("update"))==90){
            update = Integer.parseInt(request.getParameter("update"));}
            }
            out.print("<input type=\"hidden\" name=\"pagina\" value="+pagina+">");
            out.print("<input type=\"hidden\" name=\"update\" value="+update+">");
            out.print("<input type=\"hidden\" name=\"productoeli\" value="+productoup+">");
            %>
             <%
            if(pagina==1){
            out.print("<a href=\"catalogo.jsp\"><img src =\"img/regresar.png\" align=\"left\"></a>");}
            if(pagina==2){
            out.print("<a href=\"Compras.jsp\"><img src =\"img/regresar.png\" align=\"left\"></a>");
            }
            %>
              
            <%
            Statement q = null;
            ResultSet rs = null;
            String Descripcion = null;
            String Marca = null;
            String Precio = null;
            String Cantidad = null;
            String Unidad = null;
            String Categoria = null;
            q = ConexionDB.getConnection().createStatement();
            rs = q.executeQuery("SELECT abarrote.idabarrote, abarrote.descripcionabarrote,abarrote.cantidadplan,abarrote.marca,abarrote.Precio,categoria.descripcion, unidadmedida.Descripcion FROM abarrote, categoria, unidadmedida WHERE idusuario='"+Sesion.getAliasUsuario()+"' AND idabarrote="+productoup+" AND abarrote.idcategoria=categoria.idcategoria AND abarrote.idunidadmedida=unidadmedida.idunidadMedida ");
            while(rs.next())
            {
             Descripcion= rs.getString("abarrote.descripcionabarrote");
             Marca= rs.getString("abarrote.marca");
             Precio= rs.getString("abarrote.Precio");
             Cantidad= rs.getString("abarrote.cantidadplan");
             Unidad= rs.getString("unidadmedida.Descripcion");
             Categoria= rs.getString("categoria.descripcion");
                
            }
            %>
            <label><h1>Producto</h1></label>
             <%
             if(update==45){
             out.print("<input type=\"text\" name=\"productoin\" value="+Descripcion+" readonly=\"readonly\" ></br>");
             }
             else
             {
             out.print("<input type=\"text\" name=\"productoin\"></br>");
             }
             %>
            <label><h1>Marca</h1></label>
             <%if(update==45){
             out.print("<input type=\"text\" name=\"marcain\" value="+Marca+" disabled></br>");
             }
             else
             {
             out.print("<input type=\"text\" name=\"marcain\"></br>");
             
             }
             %>
            <label><h1>Precio</h1></label>
           <%if(update==45){
             out.print("<input type=\"text\" name=\"precioin\" value="+Precio+"></br>");
           }else{
                  out.print("<input type=\"text\" name=\"precioin\"></br>");   
                     }

%>
            
           
                <label><h1>Cantidad</h1></label>
                
                <select name="cantidadin" >
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                </select>
                
                </br>
                <%
                if(update==45){
                out.print("<label><h1>---->Cantidad Ant "+Cantidad+"</h1></label></br>");
                }
                %>
                
                
                <label><h1>Unidad de medida</h1></label>
                        <select name="unidadin">
                        <%
                        Statement q2 = null;
                        ResultSet rs2 = null;
                        q2 = ConexionDB.getConnection().createStatement();
                        rs2 = q2.executeQuery("SELECT * FROM unidadmedida");
                        while(rs2.next())
                        {
                         out.print("<option value="+rs2.getString("idunidadMedida")+">"+rs2.getString("Descripcion")+"</option>");
                        }
                        %>
                    </select>
                   
                </br>
                    <%
                if(update==45){
                out.print("<label><h1>-->Medida Ant "+Unidad+"</h1></label></br>");
                }
                %>
                
                    <label><h1>Categoria</h1></label>
                    
                        <%
                        if(update==45){
                        out.print("<select name=\"categoriain\" value="+Categoria+" disabled>");
                        }
                        else{
                        out.print("<select name=\"categoriain\">");}
                        Statement q3 = null;
                        ResultSet rs3=null;
                        q3 = ConexionDB.getConnection().createStatement();
                        rs3 = q3.executeQuery("SELECT * FROM categoria");
                        while(rs3.next())
                        {
                         
                            out.print("<option value="+rs3.getString("idcategoria")+">"+rs3.getString("descripcion")+"</option>");
                        }
                        %>
                    </select>
                    </br>
                    
                    <%
                        String lista = request.getParameter("lista");

                        out.print("<input type=\"hidden\" name=\"lista\" value="+lista+">"); 
                        %>
                    
                    <input type="submit" name="enviar" value="ACEPTAR">   
      </form>

                    
                    <% 
                       
                        
                        String producto = request.getParameter("productoin");
               
                        String marca = request.getParameter("marcain");

                      
                        String precio = request.getParameter("precioin");
                   
                   
                        String cantidad= request.getParameter("cantidadin");
                        
                        
                        String unidad = request.getParameter("unidadin");
                        
                    
                        String categoria= request.getParameter("categoriain");
                        
                        
                        
                       if(update==0){ 
                       if(producto=="" || marca=="" || precio=="" || producto==null){out.print("Llena los Campos para insertar un Producto");}
                        else{
                        if(pagina==1){
                        Statement q4 = null;
                        q4 = ConexionDB.getConnection().createStatement();
                        q4.executeUpdate("INSERT INTO abarrote(descripcionabarrote, marca, Precio, cantidadplan, idcategoria, idunidadmedida, idusuario) VALUES ('"+producto+"','"+marca+"',"+precio+","+cantidad+","+categoria+","+unidad+",'"+Sesion.getAliasUsuario()+"')");
                        out.print("<h1>OPERACION EXITOSA</h1>");}
                        }}
                       
                       if(update==90){ 
                           
                       
                       if(producto=="" || marca=="" || precio=="" || producto==null){out.print("Llena los Campos para insertar un Producto");}
                        else{
                        if(pagina==2){
                        
                        Statement q4 = null;
                        q4 = ConexionDB.getConnection().createStatement();
                        q4.executeUpdate("INSERT INTO abarrote(descripcionabarrote, marca, Precio, cantidadplan, idcategoria, idunidadmedida, idusuario) VALUES ('"+producto+"','"+marca+"',"+precio+","+cantidad+","+categoria+","+unidad+",'"+Sesion.getAliasUsuario()+"')");
                        Statement q5 = null;
                        ResultSet rs4 = null;
                        q5 = ConexionDB.getConnection().createStatement();
                        rs4=q5.executeQuery("SELECT * FROM abarrote WHERE descripcionabarrote='"+producto+"' AND idunidadmedida="+unidad);
                        String temp="";
                        String temp2= "";
                        while(rs4.next()){
                        temp = rs4.getString("idabarrote");
                        }
                        
                        Statement q6 = null;
                        q6 = ConexionDB.getConnection().createStatement();
                        
                        q6.executeUpdate("INSERT INTO detallelista(idlista,idabarrote, cantidadplan, cantidadcompra, abastecido) VALUES ("+lista+","+temp+","+cantidad+",0,0)");
                        out.print("<h1>OPERACION EXITOSA</h1>");}
                        }}
                       
                       if(update==45){
                           
                        if(producto=="" || marca=="" || precio=="" || producto==null){out.print("Llena los Campos para insertar un Producto");}
                        else{
                        Statement q4 = null;
                        q4 = ConexionDB.getConnection().createStatement();
                        q4.executeUpdate("UPDATE abarrote SET Precio="+precio+",cantidadplan="+cantidad+",idunidadmedida="+unidad+" WHERE idabarrote="+productoup);
                        out.print("<h1>OPERACION EXITOSA</h1>");}}
                        
                    
                        %>    
    </div>
    </body>
</html>
