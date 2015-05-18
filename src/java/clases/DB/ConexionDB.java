/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.DB;

/**
 *
 * @author cammend
 */

import clases.util.Shop;
import clases.util.Archivo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
     
    public static Connection getConnection()
    {
        Connection conexion=null;
     
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String servidor = Shop.getHostDB();
            String usuarioDB = Shop.userDB();
            String passwordDB = Shop.passDB();
            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
        }
        catch(ClassNotFoundException ex)
        {
            //JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            Archivo.guardarCadena("Error del Driver: "+ex.getMessage());
            conexion=null;
        }
        catch(SQLException ex)
        {
            //JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            Archivo.guardarCadena("Error de Conexion: "+ex.getMessage());
            conexion=null;
        }
        catch(Exception ex)
        {
            //JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion=null;
        }
        finally
        {
            return conexion;
        }
    }
}