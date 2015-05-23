/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.DB.ConexionDB;
import java.sql.Connection;


/**
 *
 * @author cammend
 */
public class Core {
    private Connection conexion = null;
    //clase de prueba aún no usada
    public void conectarDB(){
        conexion = ConexionDB.getConnection();
        if( conexion != null ){
            System.out.println("Conexion exitosa");
        }
    }
}
