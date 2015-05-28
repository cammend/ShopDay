/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

import clases.DB.IODB;
import clases.util.Archivo;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cammend
 */
public class Sesion {
    public static final String ATTR_ERROR= "Error";
    public static final String ATTR_ALIAS_USUARIO= "AliasUsuario";
    public static final String ATTR_DATOS_FORM= "DatosForm";
    private static HttpServletRequest request = null;
    
    public static void init(HttpServletRequest rq){
        request = rq;   	
    }
    
    public static void cerrar(){
    	if( haySesion() ){
    		request.getSession().removeAttribute(ATTR_ALIAS_USUARIO);
    	}
    }
    
    public static String getAttr(String attr){
        if( request.getSession() != null ){
            Object obj = request.getSession().getAttribute(attr);
            if( obj != null ){
            	Archivo.guardarCadena("getAttr("+attr+"): "+String.valueOf(obj));
            	return String.valueOf(obj);
            }else{
                Archivo.guardarCadena("No existe atributo "+attr);
            }
        }else{
            Archivo.guardarCadena("No hay sesión");
        }
        return null;
    }
    
    public static void setAttr(String attr, Object valor){
        if( request.getSession() != null ){
            request.getSession().setAttribute(attr, valor);
        }
    }
    
    public static String[] getArrayAttr(String attr){
    	Object obj;
    	if( request.getSession() != null ){
            obj = request.getSession().getAttribute(attr);
            if(obj != null){
            	if(obj instanceof ArrayList<?>){
            		ArrayList<Object> datos = (ArrayList<Object>)obj;
            		ArrayList<String> piv = new ArrayList<String>();
            		for(int i=0; i<datos.size(); i++){
            			piv.add(String.valueOf(datos.get(i)));
            		}
            		String[] enviar = new String[piv.size()+1];
            		for(int i=0; i<piv.size(); i++){
            			enviar[i] = piv.get(i);
            		}
            		return enviar;
            	}
            }
        }
    	return null;
    }
    
    public static void setAttr(String attr, Object valor[]){
    	ArrayList<Object> valores = new ArrayList();
    	for(int i=0; i<valor.length; i++){
    		valores.add(valor[i]);
    	}
    	if( request.getSession() != null ){
            request.getSession().setAttribute(attr, valores);
        }
    }
    
    public static int getError(){
    	String err = getAttr(ATTR_ERROR);
    	if(err!=null){
    		return Integer.parseInt(err);
    	}
    	return -1;
    }
    
    public static void setError(int Error){
    	setAttr(ATTR_ERROR,Error);    	
    }
    
    public static void limpiar(){
    	if( hayError() ){
    		request.getSession().removeAttribute(ATTR_ERROR);
    	}else if( hayDatosForm() ){
    		request.getSession().removeAttribute(ATTR_DATOS_FORM);
    	}
    }
    
    public static boolean hayError(){
    	return hayAttr(ATTR_ERROR);
    }
    public static boolean haySesion(){
    	return hayAttr(ATTR_ALIAS_USUARIO);
    }
    
    public static boolean hayDatosForm(){
    	return hayAttr(ATTR_DATOS_FORM);
    }
    
    private static boolean hayAttr(String attr){
    	if(request.getSession().getAttribute(attr)!=null){
            return true;
    	}
    	return false;
    }
    public static String getId(){
        return request.getSession().getId();
    }
    
    public static String getAliasUsuario(){
    	if( haySesion() ){
            String alias = String.valueOf(request.getSession().getAttribute(ATTR_ALIAS_USUARIO));
            return alias;
        }
    	return null;
    }
}
