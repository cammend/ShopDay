/**
 * Archivo javascript de validación de formulario
 * @author cammend
 */

function validarPassword(IdPass1, IdPass2){
	var p1 = document.getElementById(IdPass1).value;
	var p2 = document.getElementById(IdPass2).value;
	if(p1 != p2){
		alert("Contraseñas no coinciden");
		document.getElementById(IdPass1).value = "";
		document.getElementById(IdPass2).value = "";
		document.getElementById(IdPass1).focus();
		return false;
	}
	return true;
}

function validarCorreo(IdCorreo){
	
}

function soloNumeros(e){
	var keynum = window.event ? window.event.keyCode : e.which;
	if ((keynum == 8) || (keynum == 46))
		return true;
	return /\d/.test(String.fromCharCode(keynum));
}

function validarEntero(e) { 
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8) return true; //Tecla de retroceso (para poder borrar) 
    // dejar la línea de patron que se necesite y borrar el resto 
    //patron =/[A-Za-z]/; // Solo acepta letras 
    patron = /\d/; // Solo acepta números
    //patron = /\w/; // Acepta números y letras 
    //patron = /\D/; // No acepta números 
    // 
    te = String.fromCharCode(tecla); 
    return patron.test(te);  
}
