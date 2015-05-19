/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.DB;

import clases.util.Archivo;

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
    
    //métodos específicos
    public static boolean nuevoUsuario(String correo, String nombre, String alias, String pass){
        String f = getFechaHoy();
        String consulta ="insert into Usuarios (correo, nombre, alias, fecha, pass) values ('"+correo+"','"+nombre+"','"+alias+"','"+f+"','"+pass+"')";
        return hacerConsulta(consulta);
    }
    public static boolean existeCorreo(String correo){
        return existe("Usuarios", "correo", correo);
    }
    public static boolean existeAlias(String alias){
        return existe("Usuarios", "alias", alias);
    }
    
    //métodos genéricos
    public static boolean existe(String tabla, String campo, String valor){
        rs = traerConsulta("select "+campo+" from "+tabla+" where "+campo+"='"+valor+"'");
        if( rs!=null ){
            try{
                if( rs.next() ){
                    cerrar();
                    return true;
                }
            }catch(Exception ex){
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
            return true;
        }catch(Exception ex){
            Archivo.guardarCadena("Error al hacer la consulta: "+consulta+"\n"+ex.getMessage());
        }
        return false;
    }
    public static ResultSet traerConsulta(String consulta){
        try{
            conexion = getConexion();
            st = conexion.createStatement();
            rs = st.executeQuery(consulta);
            return rs;
        }catch(Exception ex){
            Archivo.guardarCadena("Error en la consulta: "+consulta+"\n"+ex.getMessage());
        }
        return null;
    }
    public static ResultSet getTabla(String tabla){
        return traerConsulta("select * from "+tabla);
    }
    public static ResultSet getFila(String tabla, String campo, String valor){
        return traerConsulta("select * from "+tabla+" where "+campo+"='"+valor+"'");
        //String[2] act = new String{"",""};
        //actualizar("",act,act);
    }
    public static boolean actualizar(String tabla, String actualizar[], String fila[]){
        return hacerConsulta("");
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
    private static Connection getConexion(){
       return ConexionDB.getConnection();
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
            Archivo.guardarCadena("Error cerrando IODB: "+ex.getMessage());
        }
    }
}
