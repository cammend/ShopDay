/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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

/**
 *
 * @author cammend
 */
public class insertarLista extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String descripcion;
    String fecha;
    double presupuesto = 0;
    HttpServletRequest request;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.request = request;
        Sesion.init(request);
        
        obtenerDatosFormulario();
        if( hayValoresNulos() ){
            Redirect.irA("nueva-lista.html", request, response);
        }else{
            while(true){
                int id_lista = random(1,10000000);
                if( !IODB.existeIdLista(id_lista) ){
                    Sesion.setAttr("id_lista", id_lista);
                    IODB.nuevaLista(id_lista, descripcion, fecha, presupuesto, Sesion.getAliasUsuario());
                    Redirect.irA("preparar-lista.html", request, response);
                    break;
                }
            }
        }
    }
    
    private int random(int a, int b){
        return Math.round((float)(Math.random()*b+a));
    }
    
    private void obtenerDatosFormulario(){
    	descripcion = request.getParameter("descripcion");
        fecha  = request.getParameter("fecha");
        Object piv = request.getParameter("presupuesto");
        if( piv!=null ){
            String pivote = String.valueOf(piv);
            if( !pivote.equals("") ){
                presupuesto = Double.parseDouble(pivote);
            }
        }
    }
    private boolean hayValoresNulos(){
    	if(descripcion == null){ //si los parametros estan en null            
            return true;
        }else if(descripcion.equals("") || fecha.equals("") || presupuesto==0.0 ){
        	return true;
        }
    	return false;
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
