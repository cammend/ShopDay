/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import clases.util.Archivo;
import clases.Error;
import clases.DB.IODB;
import clases.Redirect;
import clases.Sesion;
import clases.util.Shop;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    
    private HttpSession sesion = null;
    private String DatosFormulario[];
    private HttpServletRequest request = null; 
    private HttpServletResponse response = null;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sesion = request.getSession();
        this.request = request;
        this.response = response;
        Sesion.init(request); //inicializando la sesion
        
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
        	DatosFormulario = new String[] {correo,nombre,alias};
            //si todos los campos viene llenos comprobamos si las contraseñas coinciden
            if(!pass.equals(pass2)){
                sesion.setAttribute(Sesion.ATTR_ERROR , Error.PASSWORD_N_COINCIDEN);
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
                    	int codigo = IODB.getCodUsuario(alias);
                    	if(codigo == -1){//quiere decir que hay error
                    		Archivo.guardarCadena("Error obteniendo codigo de usuario");
                    	}
                        //Agregamos una variable de sesión
                    	Sesion.setAttr(Sesion.ATTR_CODIGO_USUARIO, codigo);
                    	//si ingresó el usuario redireccionamos a la pagina principal
                    	Redirect.redireccionar("/inicio.html", request, response);
                    }else{
                        mostrarPaginaError(response); //mostramos la página de error
                    }
                }catch(Exception ex){
                    Archivo.guardarCadena(ex.getMessage());
                    ex.printStackTrace();
                }
                return;
            }
            case Error.ALIAS:{
                error(Error.ALIAS);
                return;
            }
            case Error.CORREO:{
                error(Error.CORREO);
                return;
            }case Error.ALIAS_CORREO:{
                error(Error.ALIAS_CORREO);
                return;
            }
        }
    }
    
    private void error(int err){
    	try{
	    	sesion.setAttribute(Sesion.ATTR_ERROR, err);                
	        Sesion.setAttr(Sesion.ATTR_DATOS_FORM, DatosFormulario);
	        Redirect.irA(Shop.getApp()+"/registrarse.html", request, response);
    	}catch(Exception ex){
    		Archivo.guardarCadena("Error comprobando registro: "+ex.getMessage());
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
