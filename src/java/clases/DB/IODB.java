/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.DB;

import clases.util.Archivo;
import clases.util.Md5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author cammend
 */
public class IODB {
    private static Connection conexion = null;
    private static Statement st = null;
    private static ResultSet rs = null;
    private static boolean error = false;
    private static String mensaje = "";
    private static String tabla = "";
    private static String[] campo;
    
    //métodos específicos
    public static boolean nuevoUsuario(String nombre, String alias, String pass, int edad){
        pass = Md5.encriptar(pass);
        Object[] obj = new Object[] {alias,nombre,pass,edad};
        return insertarFila(TablasDB.USUARIOS, obj);
    }
    public static boolean nuevaLista(int id, String descripcion, String fecha, double presupuesto, String aliasUsuario){
        setearDatosTabla(TablasDB.LISTAS);;
        Object[] obj = new Object[] {id, descripcion, fecha, presupuesto, false, aliasUsuario};
        return insertarFila(TablasDB.LISTAS, campo, obj);
    }
    public static boolean nuevoAbarrote(String descripcion, String marca, double precio, int cantidad, int categoria, int medida, String usuario){
        setearDatosTabla(TablasDB.ABARROTES);
        String[] campos = new String[] {campo[1],campo[2],campo[3],campo[4],campo[5],campo[6],campo[7]};
        Object[] obj = new Object[] {descripcion, marca, precio, cantidad, categoria, medida, usuario};
        return insertarFila(tabla,campos,obj);
    }
    public static boolean nuevoDetalleLista(int idLista, int idAbarrote, int cantidadPlan, int cantidadCompra, boolean abastecido){
        setearDatosTabla(TablasDB.DETALLE_LISTA);
        Object[] obj = new Object[] {idLista,idAbarrote,cantidadPlan,cantidadCompra,abastecido};
        return insertarFila(tabla,campo,obj);
    }
    public static boolean existeCorreo(String correo){
    	setearDatosTabla(TablasDB.USUARIOS);
        return existe(tabla,campo[1], correo);
    }
    public static boolean existeAlias(String alias){
    	setearDatosTabla(TablasDB.USUARIOS);
    	return existe(tabla,campo[0], alias);
    }
    public static boolean existeIdLista(int id){
        setearDatosTabla(TablasDB.LISTAS);
        rs = traerConsulta("select "+campo[0]+" from "+tabla+" where "+campo[0]+" = "+id);
        try{
            if( rs!=null ){
                if( rs.next() ){
                    return true;
                }
            }
        }catch(Exception ex){
            Archivo.guardarCadena("Error existe Id Lista");
        }
        return false;
    }
    public static boolean existeAbarrote(String nombreAbarrote){
        setearDatosTabla(TablasDB.ABARROTES);
        return existe(tabla,campo[1],nombreAbarrote);
    }
    public static boolean nuevoAbarrote(String nombre){
    	Object[] obj = new Object[] {nombre};
    	return insertarFila(TablasDB.ABARROTES, obj);
    }
    public static boolean nuevaLista(int cod, String nombreAbarrote, int cantidad){
    	return true;
    }
    public static int getCodUsuario(String alias){
    	setearDatosTabla(TablasDB.USUARIOS);
    	int codigo;
    	rs = getCampoFila(campo[0],campo[1],alias);
    	codigo = getNumeroFromRS(rs,campo[0]);
    	if(codigo!=-1){
    		cerrar();
    		return codigo;
    	}
    	rs = getCampoFila(campo[0],campo[3],alias);
    	return getNumeroFromRS(rs,campo[0]);
    }
    public static int getIdAbarrote(String nombre){
        setearDatosTabla(TablasDB.ABARROTES);
        ResultSet result = getCampoFila(campo[0],campo[1],nombre);
        int id = getNumeroFromRS(result,campo[0]);
        return id;
    }
    public static String getPasswordUsuario(String user){
    	setearDatosTabla(TablasDB.USUARIOS);
    	String pass;
    	rs = getCampoFila(campo[2],campo[1],user);//consiguiendo el pass con el correo
    	pass = getStringFromRS(rs,campo[2]);
    	if(pass!=null){
    		cerrar();
    		return pass;//pass obtenida con el correo
    	}
    	rs = getCampoFila(campo[2],campo[0],user);//consiguiendo el pass con el alias
    	pass = getStringFromRS(rs,campo[2]);
    	if(pass!=null){
    		cerrar();
    		return pass;//pass obtenida con el alias
    	}
    	return null;
    }
    public static Object[] getDatosUsuario(int codigo){
    	setearDatosTabla(TablasDB.USUARIOS);
    	rs = getFila(tabla,campo[0],codigo);
    	return generarFilaObjetos(rs,campo);
    }
    public static Integer[] getColumnaIdMedida(){
        setearDatosTabla(TablasDB.MEDIDA);
        Object[] obj = getColumnaTabla(tabla,campo[0]);
        if( obj != null){
            Integer id[] = new Integer[obj.length];
            for(int i=0; i<obj.length; i++){
                id[i] = Integer.valueOf(String.valueOf(obj[i]));
            }
            return id;
        }
        return null;
    }
    public static Integer[] getColumnaIdCategoria(){
        setearDatosTabla(TablasDB.CATEGORIA);
        Object[] obj = getColumnaTabla(tabla,campo[0]);
        if( obj != null){
            Integer id[] = new Integer[obj.length];
            for(int i=0; i<obj.length; i++){
                id[i] = Integer.valueOf(String.valueOf(obj[i]));
            }
            return id;
        }
        return null;
    }
    public static String[] getColumnaDescripcionMedida(){
        setearDatosTabla(TablasDB.MEDIDA);
        Object[] obj = getColumnaTabla(tabla,campo[1]);
        if( obj != null){
            String descripcion[] = new String[obj.length];
            for(int i=0; i<obj.length; i++){
                descripcion[i] = String.valueOf(obj[i]);
            }
            return descripcion;
        }
        return null;
    }
    public static String[] getColumnaDescripcionCategoria(){
        setearDatosTabla(TablasDB.CATEGORIA);
        Object[] obj = getColumnaTabla(tabla,campo[1]);
        if( obj != null){
            String descripcion[] = new String[obj.length];
            for(int i=0; i<obj.length; i++){
                descripcion[i] = String.valueOf(obj[i]);
            }
            return descripcion;
        }
        return null;
    }
    public static Object[] getDatosLista(int codigo){
        setearDatosTabla(TablasDB.LISTAS);
        rs = getFila(tabla,campo[0],codigo);
        return generarFilaObjetos(rs,campo);
    }
    
    //métodos genéricos
    public static Object[] generarFilaObjetos(ResultSet rs, String campo[]){
    	Object obj[] = new Object[campo.length];
    	if(rs!=null){
    		try{
	    		if(rs.next()){
	    			for(int i=0; i<campo.length; i++){
	    				obj[i] = rs.getObject(campo[i]);
	    			}
	    			cerrar();
	    			return obj;
	    		}
    		}catch(Exception ex){
    			error = true;
    			Archivo.guardarCadena("Error obteniendo cadena: "+ex.getMessage());
    		}
    	}
    	cerrar();
    	return null;
    }
    public static String getStringFromRS(ResultSet rs,String campo){
    	if(rs!=null){
    		try{
    		if(rs.next()){
    			return rs.getString(campo);
    		}
    		}catch(Exception ex){
    			error = true;
    			Archivo.guardarCadena("Error obteniendo cadena: "+ex.getMessage());
    		}
    	}
    	cerrar();
    	return null;
    }
    public static int getNumeroFromRS(ResultSet rs,String campo){
    	if(rs!=null){
    		try{
    		if(rs.next()){
    			return rs.getInt(campo);
    		}
    		}catch(Exception ex){
    			error = true;
    			Archivo.guardarCadena("Error obteniendo número: "+ex.getMessage());
    		}
    	}
    	cerrar();
    	return -1;
    }
    public static boolean existe(String tabla, String campo, String valor){
        rs = traerConsulta("select "+campo+" from "+tabla+" where "+campo+"='"+valor+"'");
        if( rs!=null ){
            try{
                if( rs.next() ){
                    cerrar();
                    return true;
                }
            }catch(Exception ex){
            	error = true;
                Archivo.guardarCadena("Error comprobando "+campo+": "+ex.getMessage());
            }
        }
        cerrar();
        return false;
    }
    public static boolean hacerConsulta(String consulta){
        try{
            conexion = getConexion();
            st = conexion.createStatement();
            st.executeUpdate(consulta);
            cerrar();
            return true;
        }catch(Exception ex){
        	error = true;
            Archivo.guardarCadena("Error al hacer la consulta: "+consulta+"\n"+ex.getMessage());
        }
        cerrar();
        return false;
    }
    public static ResultSet traerConsulta(String consulta){
        try{
            conexion = getConexion();
            st = conexion.createStatement();
            rs = st.executeQuery(consulta);
            return rs;
        }catch(Exception ex){
        	error = true;
            Archivo.guardarCadena("Error en la consulta: "+consulta+"\n"+ex.getMessage());
        }
        cerrar();
        return null;
    }
    public static ResultSet getTabla(String tabla){
        return traerConsulta("select * from "+tabla);
    }
    public static ResultSet getFila(String tabla, String campo, String valor){
        return traerConsulta("select * from "+tabla+" where "+campo+"='"+valor+"'");
    }
    public static ResultSet getFila(String tabla, String campo, int valor){
        return traerConsulta("select * from "+tabla+" where "+campo+"="+valor);
    }
    public static ResultSet getCampoFila(String campo, String fila, Object valor){
    	if( valor instanceof Integer ){
    		return traerConsulta("select "+campo+" from "+tabla+" where "+fila+"="+valor);
    	}
    	return traerConsulta("select "+campo+" from "+tabla+" where "+fila+"='"+valor+"'");
    }
    public static boolean actualizar(String tabla, String actualizar[], String fila[]){
    	String act = armarCampos(actualizar);
    	String c = "Update "+tabla+" set "+act+" where ("+fila[0]+"='"+fila[1]+"')";
    	return hacerConsulta(c);
    }
    public static boolean insertarFila(String tabla, String campo[], Object valores[]){
    	if(campo.length == 0){
            Archivo.guardarCadena("Error al insertar fila: no hay algo que insertar.");
            return false;
    	}
    	String campos = armarCampos(campo);
    	String valor = armarValores(valores);
    	String consulta ="insert into "+tabla+campos+"values"+valor;
        return hacerConsulta(consulta);
    }
    public static boolean insertarFila(String tabla, Object valores[]){
    	return insertarFila(tabla, TablasDB.getCamposTabla(tabla),valores);
    }
    public static Object[] nextFilaTabla(ResultSet rs, String campo[]){
        Object[] obj = new Object[campo.length];
        try{
            if( rs!=null ){
                while( rs.next() ){
                    for(int i=0; i<campo.length; i++){
                        obj[i] = rs.getObject(campo[i]);
                    }
                }
                return obj;
            }
        }catch(Exception ex){
            Archivo.guardarCadena("Error armando Array de tabla. "+ex.getMessage());
        }
        return null;
    }
    public static int getCantidadFilasTabla(String tabla){
        rs = traerConsulta("select COUNT(*) from "+tabla);
        try{
            if( rs!= null ){
                if( rs.next() ){
                    return rs.getInt("COUNT(*)");
                }
            }
        }catch(Exception ex){
            Archivo.guardarCadena("Error en consulta COUNT(): "+ex.getMessage());
        }
        return -1;
    }
    public static Object[] getColumnaTabla(String tabla, String columna){
        int num = getCantidadFilasTabla(tabla);
        if( num > 0){
            Object[] obj = new Object[num];
            rs = traerConsulta("select "+columna+" from "+tabla);
            try{
                if( rs!=null ){
                    for(int i=0; rs.next(); i++){
                        obj[i] = rs.getObject(columna);
                    }
                    cerrar();
                    return obj;
                }
            }catch(Exception ex){
                Archivo.guardarCadena("Error en getColumnaTabla");
            }
        }
        return null;
    }

    //métodos útiles
    private static String getFechaHoy(){
        Calendar fecha = Calendar.getInstance();
        String a = String.valueOf(fecha.get(Calendar.YEAR));
        String m = String.valueOf(fecha.get(Calendar.MONTH)+1);
        String d = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));
        String f = a+"-"+m+"-"+d;
        return f;
    }
    private static String armarCampos(String campos[]){
    	//se supone que el array campos tiene al menos un campo
    	String campo = " (";
    	for(int i=0; i<campos.length; i++){
    		campo += campos[i]+",";
    	}
    	campo += "REPLACE";
    	campo = campo.replace(",REPLACE", ") ");
    	return campo;
    }
    private static String armarValores(Object valores[]){
    	//se supone que el array valores tiene al menos un valor
    	//se va a comprobar valores de cadenas o números
    	String valor = " (";
        for (Object valore : valores) {
            if (valore instanceof String) {
                valor += "'" + valore + "',";
            } else if (valore instanceof Integer) {
                valor += valore + ",";
            } else if (valore instanceof Double) {
                valor += valore + ",";
            }else{
                valor += valore + ",";
            }
        }
    	valor += "REPLACE";
    	valor = valor.replace(",REPLACE", ")");
    	return valor;
    }
    public static String armarArray(Object valores[], String init, String fin){
        String valor = init;
        for (Object valore : valores) {
            if (valore instanceof String) {
                valor += "'" + valore + "',";
            } else if (valore instanceof Integer) {
                valor += valore + ",";
            } else if (valore instanceof Double) {
                valor += valore + ",";
            }else{
                valor += valore + ",";
            }
        }
    	valor += "REPLACE";
    	valor = valor.replace(",REPLACE", fin);
    	return valor;
    }
    public static void setearDatosTabla(String t){
    	tabla = t;
    	campo = TablasDB.getCamposTabla(t);
    }
    private static Connection getConexion(){
       return ConexionDB.getConnection();
    }
    public static boolean hayError(){
    	if( error ){
    		error = false;
    		return true;
    	}
    	return false;
    }
    public static void cerrar(){
        try{
            if( rs!=null ){
                rs.close(); rs=null;
            }
            if( st!=null ){
                st.close(); st=null;
            }
            if( conexion!=null ){
                conexion.close(); conexion=null;
            }
        }catch(Exception ex){
        	error = true;
            Archivo.guardarCadena("Error cerrando IODB: "+ex.getMessage());
        }
    }
}
