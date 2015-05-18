/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.util;

import clases.util.Archivo;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author cammend
 */
public class Shop {
    
    public static String getPathError(){
        String u = getUname();
        if( u != null ){
            switch(u){
                case "cammend-pc": return "/home/cammend/shopday.log";
                default: return "/home/ubuntu/shopday.log";
            }            
        }
        return null;
    }
    public static String getHost(){
        return "http://localhost:8080";
    }
    public static String getApp(){
        return "/ShopDay";
    }
    public static String getHostDB(){
        return "jdbc:mysql://127.0.0.1/shopday";
    }
    public static String userDB(){
        return "root";
    }
    public static String passDB(){
        return "chopday1801";
    }
    public static String getDB(){
        return "shopday";
    }
    public static String getVersionKernel(){
        return null;
    }
    private static String getUname(){
        try{
            Process proceso = Runtime.getRuntime().exec("uname -n");
            InputStreamReader isr = new InputStreamReader(proceso.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String linea;
            if( (linea=br.readLine()) != null ){
                return linea;
            }
        }catch(Exception ex){
            Archivo.guardarCadena("Error en getUname: uname -n\n"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
