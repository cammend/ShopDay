package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.Redirect;
import clases.Error;
import clases.Sesion;
import clases.DB.IODB;
import clases.util.Archivo;
import clases.util.Md5;
import clases.util.Shop;

/**
*
* @author cammend
*/
@WebServlet("/comprobarLogin")
public class comprobarLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String nombre = null;
	private String pass = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request; this.response = response;
        Sesion.init(request);//inicializando la sesion
        
        obtenerDatosFormulario();
        if( hayValoresNulos() ){
        	Redirect.irA(Shop.getApp()+"/login.html", request, response);
        }
        comprobarDatosLogin();        
    }
    
    private void comprobarDatosLogin() throws IOException{
    	Sesion.setAttr(Sesion.ATTR_DATOS_FORM, nombre);
    	if( IODB.existeAlias(nombre) || IODB.existeCorreo(nombre) ){
    		String passDB = IODB.getPasswordUsuario(nombre);
        	if(passDB!=null){
        		pass = Md5.encriptar(pass);
        		if(passDB.equals(pass)){
	                    //Agregamos una variable de sesión
                            Sesion.setAttr(Sesion.ATTR_ALIAS_USUARIO, nombre);
                            //si ingresó el usuario redireccionamos a la pagina principal
                            Redirect.irA("inicio.html", request, response);
        		}else{
        			Sesion.setError(Error.PASSWORD_N_COINCIDEN);
        			Redirect.irA("login.html", request, response);
        		}
        	}
    	}else if( IODB.hayError() ){
    		mostrarPaginaError(response);
    	}else{
    		Sesion.setError(Error.ALIAS_CORREO_N_EXISTE);
    		Redirect.irA("login.html", request, response);
    	}
    }
    private void obtenerDatosFormulario(){
    	nombre = request.getParameter("nombre");
        pass  = request.getParameter("pass");
    }
    private boolean hayValoresNulos(){
    	if(nombre == null){ //si los parametros estan en null            
            return true;
        }else if(nombre.equals("") || pass.equals("")){
        	return true;
        }
    	return false;
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
