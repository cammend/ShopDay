/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.util.Archivo;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cammend
 */
public class Sesion {
    public static final String ATTR_ERROR_REGISTRO= "ErrorRegistro";
    public static final String ATTR_NOMBRE_USUARIO= "NombreUsuario";

    public static String getAttr(HttpServletRequest request, String attr){
        if( request.getSession() != null ){
            Object obj = request.getSession().getAttribute(attr);
            if( obj != null ){
              return String.valueOf(obj);
            }else{
                Archivo.guardarCadena("No existe atributo ErrorRegistro");
            }
        }else{
            Archivo.guardarCadena("No hay sesión");
        }
        return null;
    }
    
    public static void setAttr(HttpServletRequest request, String attr, Object valor){
        if( request.getSession() != null ){
            request.getSession().setAttribute(attr, valor);
        }
    }
}
