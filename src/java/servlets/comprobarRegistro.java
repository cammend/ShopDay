/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import clases.util.Archivo;
import clases.DB.ConexionDB;
import clases.Error;
import clases.DB.IODB;
import clases.Redirect;
import clases.Sesion;
import clases.util.Shop;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cammend
 */
public class comprobarRegistro extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    private Connection conexion = null;
    private Statement st = null;
    private ResultSet rs = null;
    private HttpSession sesion = null;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        //comprobamos las variables del formulario
        String correo = request.getParameter("correo");
        String nombre  = request.getParameter("nombre");
        String alias  = request.getParameter("alias");
        String pass   = request.getParameter("pass");
        String pass2  = request.getParameter("pass2");
        if(correo == null){ //si los parametros estan en null
            Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
            return;
        }else if(correo.equals("") || nombre.equals("") || alias.equals("") || pass.equals("") || pass2.equals("")){
            //si vienen vacios volvemos a la pagina de registro
            Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
            return;
        }else{
            //si todos los campos viene llenos comprobamos si las contraseñas coinciden
            if(!pass.equals(pass2)){
                sesion.setAttribute(Sesion.ATTR_ERROR_REGISTRO , Error.PASSWORD_N_COINCIDEN);
                Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
                return;
            }
        }
        //el metodo comprobarReg revisa que los datos no esten repetido o que sean validos
        int cod = comprobarReg(correo, alias);
        switch (cod){
            case Error.OK :{
                try{  //ingresamos los datos a la DB
                    if( IODB.nuevoUsuario(correo, nombre, alias, pass) ){
                        //si ingresó el usuario redireccionamos a la pagina principal
                    }else{
                        mostrarPaginaError(response); //mostramos la página de error
                    }
                    //agregamos a la sesion el nombre de usuario
                    Sesion.setAttr(request, Sesion.ATTR_NOMBRE_USUARIO, alias);
                }catch(Exception ex){
                    Archivo.guardarCadena(ex.getMessage());
                    ex.printStackTrace();
                }
                return;
            }
            case Error.ALIAS:{
                sesion.setAttribute(Sesion.ATTR_ERROR_REGISTRO, Error.ALIAS);
                Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
                return;
            }
            case Error.CORREO:{
                sesion.setAttribute(Sesion.ATTR_ERROR_REGISTRO, Error.CORREO);
                Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
                return;
            }case Error.ALIAS_CORREO:{
                sesion.setAttribute(Sesion.ATTR_ERROR_REGISTRO, Error.ALIAS_CORREO);
                Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
                return;
            }
        }
    }
    
    private int comprobarReg(String correo, String alias){
        boolean c = IODB.existeCorreo(correo);
        boolean a = IODB.existeAlias(alias);
        if( c && a ){
            return Error.ALIAS_CORREO;
        }else if( c ){
            return Error.CORREO;
        }else if( a ){
            return Error.ALIAS;
        }else{
            return Error.OK;
        }
    }

    private void mostrarPaginaError(HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error al procesar la solicitud</h1>");
            out.println("<p>Ha ocurrido un error en el servidor.</p>");
            out.println("<p>Sentimos los inconvenientes.</p>");
            out.println("<a href=\""+Shop.getApp()+"\">Ir al inicio</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
