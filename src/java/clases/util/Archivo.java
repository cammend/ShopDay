/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.util;


import java.io.*;
import java.util.Date;

/**
 *
 * @author cammend
 */
public class Archivo {
    
    private static File file = null;
    private static FileWriter fw = null;
    private static FileReader fr = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
        
    private static void crearArchivo(String nombre){
        try{
            file = new File(nombre);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static String guardarCadena(String cadena){
        crearArchivo(Shop.getPathError());
        try{
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            Date fecha = new Date();
            bw.write("### "+fecha.toString()+" ###\n");
            bw.write(cadena+"\n\n");
            bw.close();
            fw.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
        return null;
    }
    
    public static BufferedReader obtenerTexto(String archivo){
        try{
            file = new File(archivo);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            return br;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String obtenerLinea(String archivo){
        if( br == null ){
            obtenerTexto(archivo);
        }
        try{
            return br.readLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void cerrar(){
        try{
            if( br != null ){
                br.close(); br = null;
            }
            if( bw != null ){
                bw.close(); bw = null;
            }
            if( fr != null ){
                fr.close(); fr = null;
            }
            if( fw != null ){
                fw.close(); fw = null;
            }
            if( file != null ){
                file = null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}