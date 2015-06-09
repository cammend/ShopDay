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
<script type="text/javascript" src="js/validacion.js"></script>
<script type="text/javascript" src="js/dinamico.js"></script>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	a{
		background: gray;
		color: white;
		padding: 0 5px;
		text-decoration: none;
	}
        body{
                background: url(img/Aquarius.png);
        }
	input{
		width: 148px;
	}
	select{
		width: 174px;
	}
	.principal{
		background: blue;
		margin: 15px 0;
		margin-left: auto;
		margin-right: auto;
		padding: 1px;
		text-align: center;
		width: 95%;
	}
	.cabecera{
		display: none;
	}
        .clase-input{
                width: 168px;
        }
	.numeros{
		display: inline-block;
		width: 100px;
	}
	.inputs{
		display: block;
	}
	.filas{
		display: block;
		margin: 15px;
		text-align: left;
		width: 100%;
	}
	#datos-form{
		background: white;
		margin-left: auto;
		margin-right: auto;
		text-align: center;
		width: 300px;
	}
	#num-filas{
		display: none;
	}

	@media screen and (min-width: 500px) and (max-width: 800px){
		input{
			width: 240px;
		}
		select{
			width: 266px;
		}
                .clase-input{
                        width: 260px;
                }
		.numeros{
			width: 150px;
		}
		#datos-form{
			width: 450px;
		}
	}

	@media screen and (min-width: 800px){
		input{
			width: 126px;
		}
		section{
			display: table;
			table-layout: fixed;
		}
		select{
			width: 110px;
		}
                .clase-input{
                        width: 126px;
                }
		.filas{
			display: table-row;
		}
		.cabecera{
			display: table-row;
		}
		.cabecera h5{
			display: table-cell;
			padding: 5px;
		}
		.inputs{
			display: table-cell;
		}
		.numeros{
			display: none;
		}
		#agregar-hidden{
			display: none;
		}
		#datos-form{
			width: 750px;
		}
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
var cantidad = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
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
    <header class="principal">
		<h1>Preparar Lista de Abarrotes</h1>
		<p id="subtitle">Preparados</p>
    </header>
    <section class="principal">
	<form id="datos" name="datos" method="post" action="guardar-lista-preparada">
            <input id="num-filas" name="num-filas" value=""/>
            <div id="datos-form">
                <a id="agregar-hidden" href="#" onclick="addProducto()">Agregar Nuevo Producto</a>
                <div class="cabecera">
                    <h5>Producto <a id="agregar" href="#" onclick="addProducto()"> + </a></h5>
                    <h5>Marca</h5>
                    <h5>Precio</h5>
                    <h5>Cantidad</h5>
                    <h5>Categoría</h5>
                    <h5>Medida</h5>
                </div>
            </div>
            <input type="submit" value="Guardar"/>
	</form>
    </section>

</body>
</html>
