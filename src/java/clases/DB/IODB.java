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
    public static boolean nuevoUsuario(String correo, String nombre, String alias, String pass){
        String fecha = getFechaHoy();
        pass = Md5.encriptar(pass);
        Object[] obj = new Object[] {correo,nombre,alias,fecha,pass};
        return insertarFila(TablasDB.USUARIOS, obj);
    }
    public static boolean existeCorreo(String correo){
    	setearDatosTabla(TablasDB.USUARIOS);
        return existe(tabla,campo[1], correo);
    }
    public static boolean existeAlias(String alias){
    	setearDatosTabla(TablasDB.USUARIOS);
    	return existe(tabla,campo[3], alias);
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
    public static String getPasswordUsuario(String user){
    	setearDatosTabla(TablasDB.USUARIOS);
    	String pass;
    	rs = getCampoFila(campo[5],campo[1],user);//consiguiendo el pass con el correo
    	pass = getStringFromRS(rs,campo[5]);
    	if(pass!=null){
    		cerrar();
    		return pass;//pass obtenida con el correo
    	}
    	rs = getCampoFila(campo[5],campo[3],user);//consiguiendo el pass con el alias
    	pass = getStringFromRS(rs,campo[5]);
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
    public static boolean insertarFila(String tabla, String campos[], Object valores[]){
    	if(campos.length != valores.length){
    		Archivo.guardarCadena("Error al insertar fila: el número de campos no coincide con el número de valores.");
    		return false;
    	}else if(campos.length == 0){
    		Archivo.guardarCadena("Error al insertar fila: no hay algo que insertar.");
    		return false;
    	}
    	String campo = armarCampos(campos);
    	String valor = armarValores(valores);
    	String consulta ="insert into "+tabla+campo+"values"+valor;
        return hacerConsulta(consulta);
    }
    public static boolean insertarFila(String tabla, Object valores[]){
    	return insertarFila(tabla, TablasDB.getCamposTabla(tabla),valores);
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
    	for(int i=0; i<valores.length; i++){
    		if(valores[i] instanceof String){
    			valor += "'"+valores[i]+"',";
    		}else if(valores[i] instanceof Integer){
    			valor += valores[i]+",";
    		}
    	}
    	valor += "REPLACE";
    	valor = valor.replace(",REPLACE", ")");
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
