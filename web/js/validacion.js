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
