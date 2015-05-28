//requiere jquery


function eliminarFila(numero){
	$('#'+numero).remove();
}
function addProducto(){
	var tr = $('<tr id="'+numFilas+'"></tr>');
	crearTDyAdd($('<button onclick="eliminarFila('+numFilas+')">X</button><input type="text" name="producto'+numFilas+'" id="producto'+numFilas+'" class="clase-producto" placeholder="Producto" required>')).appendTo(tr);
	crearTDyAdd(addSelect(categoriaID,categoria,categoriaSize,'categoria'+numFilas)).appendTo(tr);
	crearTDyAdd(addSelect(medidaID,medida,medidaSize,'medida'+numFilas)).appendTo(tr);
	crearTDyAdd(addSelect(cantidad,cantidad,cantidadSize,'cantidad'+numFilas)).appendTo(tr);
	tr.appendTo('#lista table');
	numFilas++;
        $('#filas').attr('value',numFilas);
}

function crearTDyAdd(etiqueta){
	var td = $('<td></td>');
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