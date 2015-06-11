/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
public class core extends HttpServlet {

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
        String userPath = request.getServletPath();
        String url;
        Sesion.init(request);
        //Archivo.guardarCadena("Entrando.....:"+userPath);
        
        switch (userPath) {
            case "/index":
                url = "index.jsp";
                break;
            case "/inicio.html":
                url = "inicio.jsp";
                break;
            case "/login.html":
                url = "login.jsp";
                break;
            case "/registrarse.html":
                url = "registrarse.jsp";
                break;
            case "/debug.html":
                url = "debug.jsp";
                break;
            case "/logout.html":
                url = "inicio.jsp"; Sesion.cerrar();
                break;
            case "/nueva-lista.html":
                url = "preparaLista.jsp";
                break;
            case "/comprar-lista.html":
                url = "Compras.jsp";
                break;
            case "/preparar-lista.html":
                url = "preparaListaAbarrotes.jsp";
                break;
            default:
                url = "error.jsp";
                break;
        }
        
        Redirect.redireccionar(url, request, response);
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
