//requiere jquery


function eliminarFila(numero){
	$('#fila'+numero).remove();
}
function addProducto(){
	var tr = $('<div id="fila'+numFilas+'" class="filas"></div>');
	crearTDyAdd($('<a onclick="eliminarFila('+numFilas+')">X</a><input type="text" name="producto'+numFilas+'" id="producto'+numFilas+'" class="clase-producto" placeholder="Producto" required>'),"Producto").appendTo(tr);
	crearTDyAdd($('<input type="text" name="marca'+numFilas+'" id="marca'+numFilas+'" class="clase-input" placeholder="Marca" required>'),"Marca").appendTo(tr);
        crearTDyAdd($('<input type="text" name="precio'+numFilas+'" id="precio'+numFilas+'" class="clase-input" placeholder="Precio" onkeypress="return soloNumeros(event);" required>'),"Precio").appendTo(tr);
        crearTDyAdd(addSelect(cantidad,cantidad,cantidadSize,'cantidad'+numFilas),"Cantidad").appendTo(tr);
        crearTDyAdd(addSelect(categoriaID,categoria,categoriaSize,'categoria'+numFilas),"Categoría").appendTo(tr);
	crearTDyAdd(addSelect(medidaID,medida,medidaSize,'medida'+numFilas),"Medida").appendTo(tr);
	tr.appendTo('#datos-form');
	numFilas++;
        $('#num-filas').attr('value',numFilas);
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

function addSelect(arrayValor, arrayCont, size, name){
    var select = $('<select name="'+name+'"></select>');
    for(var i=0; i<size; i++){
        var option = $('<option value="'+arrayValor[i]+'">'+arrayCont[i]+'</option>');
        option.appendTo(select);
    }
    return select;
}
