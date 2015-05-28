<%-- 
    Document   : preparaListaAbarrotes
    Created on : 27/05/2015, 01:29:24 AM
    Author     : cammend
--%>

<%@page import="clases.Sesion"%>
<%@page import="clases.DB.IODB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Preparar Lista Abarrotes</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/dinamico.js"></script>
<link rel="stylesheet" type="text/css" href="css/base-l.css">
<style>
table{
	margin-top: 15px;
	width: 100%;
}
.clase-producto{
    width: 80%;
}
#opciones{
    width: 100%;
    margin-top: 15px;
    text-align: center;
}
#add{
    padding: 0px 5px;
    background: rgb(100,100,100);
}
#cuerpo a{
    padding: 0px 5px;
    margin: 15px;
    color: white;
    text-decoration: none;
    background: rgb(100,100,100);
}#filas{
    display: none;
}
</style>
<script>
var descripcion = "descripcion";
var fecha = "2015-1-1";
//variables de la categoria
var categoria = [];
var categoriaID = [];
var categoriaSize = 0;
//variables de la medida
var medida = [];
var medidaID = [];
var medidaSize = 0;
//var cantidad
var cantidad = [1,2,3,4,5,6,7,8,9,10];
var cantidadSize = 10;

var numFilas = 0;
<%
    Sesion.init(request);
    
    Object[] obj = IODB.getDatosLista(Integer.parseInt(String.valueOf(Sesion.getAttr("id_lista"))));
    if( obj!=null ){
        out.println("descripcion = \""+String.valueOf(obj[1])+"\"");
        out.println("fecha = \""+String.valueOf(obj[2])+"\"");
    }
    
    String categorias[] = IODB.getColumnaDescripcionMedida();
    Integer id[] = IODB.getColumnaIdMedida();
    if(categorias!=null && id!=null){
        String categoria = IODB.armarArray(categorias, "[", "]");
        String ids = IODB.armarArray(id, "[", "]");
        out.println("medida = "+categoria+";");
        out.println("medidaID = "+ids+";");
        out.println("medidaSize = "+id.length);
    }
    
    categorias = IODB.getColumnaDescripcionCategoria();
    id = IODB.getColumnaIdMedida();
    if(categorias!=null && id!=null){
        String categoria = IODB.armarArray(categorias, "[", "]");
        String ids = IODB.armarArray(id, "[", "]");
        out.println("categoria = "+categoria+";");
        out.println("categoriaID = "+ids+";");
        out.println("categoriaSize = "+id.length);
    }
%>

var subtitulo = "Lista "+descripcion+" del "+fecha+"<a href=\"\">Lista Favorita</a>";

$(document).ready(function (){
    $('#subtitle').html(subtitulo);
    addProducto();
});

</script>
</head>

<body>
<div id="cuerpo">

	<div id="logo">
		<h1>Preparar Lista de Abarrotes</h1>
		<p id="subtitle">Preparados
                
		</p>
	</div>
	<div id="lista">
	<form name="datos" method="post" action="guardar-lista-preparada">
	<table border=1>
            <input id="filas" name="filas" type="text" value="hola"/>
		<tr>
			<td>Producto <%out.print("<a id=\"add\" onclick=\"addProducto()\">+</a>");%></td>
			<td>Categoría</td>
			<td>Medida</td>
			<td>Cantidad</td>
		</tr>
	</table>
            <div id="opciones">   
                <input type="submit" value="GUARDAR"/>                
            </div>
           
	</form>
	</div>
		
</div>
</body>
</html>
