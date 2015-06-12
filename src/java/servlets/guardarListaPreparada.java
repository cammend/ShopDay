/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import clases.DB.IODB;
import clases.Redirect;
import clases.Sesion;
import clases.util.Archivo;
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
public class guardarListaPreparada extends HttpServlet {

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
        Sesion.init(request);
        String fila = request.getParameter("num-filas");//este atributo me indica el numero de filas de productos
        int codigo = Integer.parseInt(Sesion.getAttr("id_lista"));
        String alias = Sesion.getAliasUsuario();
        Object datosLista[] = IODB.getDatosLista(codigo);
        String producto, marca;
        double precio;
        int cantidad, categoria, medida;        
        if( fila!=null && alias!=null ){
            int index = Integer.parseInt(fila);
            for(int i=0; i<index; i++){
                producto = request.getParameter("producto"+i);
                marca = request.getParameter("marca"+i);
                if(producto!=null){
                    precio = Double.parseDouble(request.getParameter("precio"+i));
                    cantidad = Integer.parseInt(request.getParameter("cantidad"+i));
                    categoria = Integer.parseInt(request.getParameter("categoria"+i));
                    medida = Integer.parseInt(request.getParameter("medida"+i));
                    if( !IODB.existeAbarrote(producto) ){
                        IODB.nuevoAbarrote(producto, marca, precio, cantidad, categoria, medida, alias);
                        int idA = IODB.getIdAbarrote(producto);
                        IODB.nuevoDetalleLista(codigo, idA, cantidad, 0, false);
                        Sesion.setAttr("Consulta", "OK");
                        Redirect.irA("inicio.html", request, response);
                    }else{
                        Archivo.guardarCadena("Producto Existe!");
                        int idA = IODB.getIdAbarrote(producto);
                        IODB.nuevoDetalleLista(codigo, idA, cantidad, 0, false);
                        Sesion.setAttr("Consulta", "OK");
                        Redirect.irA("inicio.html", request, response);
                    }
                }else{
                    Archivo.guardarCadena("Producto nulo!");
                }
            }
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
