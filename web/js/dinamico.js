//requiere jquery


function eliminarFila(numero){
	$('#fila'+numero).remove();
        numInputs--;
        mostrarCargar();
}
function addProducto(valorProd,valorMarca,precio,canSel,catSel,medSel){
	var tr = $('<div id="fila'+numFilas+'" class="filas"></div>');
	crearTDyAdd($('<a onclick="eliminarFila('+numFilas+')">-</a><input value="'+valorProd+'" type="text" name="producto'+numFilas+'" id="producto'+numFilas+'" class="clase-producto" placeholder="Producto" required>'),"Producto").appendTo(tr);
	crearTDyAdd($('<input value="'+valorMarca+'" type="text" name="marca'+numFilas+'" id="marca'+numFilas+'" class="clase-input" placeholder="Marca" required>'),"Marca").appendTo(tr);
        crearTDyAdd($('<input value="'+precio+'" type="text" name="precio'+numFilas+'" id="precio'+numFilas+'" class="clase-input" placeholder="Precio" onkeypress="return soloNumeros(event);" required>'),"Precio").appendTo(tr);
        crearTDyAdd(addSelect(cantidad,cantidad,cantidadSize,'cantidad'+numFilas,canSel),"Cantidad").appendTo(tr);
        crearTDyAdd(addSelect(categoriaID,categoria,categoriaSize,'categoria'+numFilas,catSel),"Categoría").appendTo(tr);
	crearTDyAdd(addSelect(medidaID,medida,medidaSize,'medida'+numFilas,medSel),"Medida").appendTo(tr);
	tr.appendTo('#datos-form');
	numFilas++;
        numInputs++;
        $('#num-filas').attr('value',numFilas);
        ocultarCargar();
}

function crearTDyAdd(etiqueta,nombre){
	var td = $('<div class="inputs"></div>');
        var span = $('<span class="numeros">'+nombre+'</span>');
        span.appendTo(td);
	etiqueta.appendTo(td);
	return td;
}

function addSelect(array, size, name){
        alert(size);
	var select = $('<select name="'+name+'"></select>');
	for(var i=0; i<size; i++){
		var option = $('<option value="'+array[i]+'">'+array[i]+'</option>');
		option.appendTo(select);
	}
	return select;
}

function addSelect(arrayValor, arrayCont, size, name, seleccion){
    var select = $('<select name="'+name+'"></select>');
    seleccion = seleccion - 1;
    for(var i=0; i<size; i++){
        if( seleccion === i ){
            var option = $('<option value="'+arrayValor[i]+'" selected="selected">'+arrayCont[i]+'</option>');
        }else{
            var option = $('<option value="'+arrayValor[i]+'">'+arrayCont[i]+'</option>');
        }
        option.appendTo(select);
    }
    return select;
}
