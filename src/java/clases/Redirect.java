/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import clases.util.Archivo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cammend
 */
public class Redirect {
    
    public static void redireccionar(String pagina, HttpServletRequest request, HttpServletResponse response){
        String url = pagina;
        HttpServletRequest req = request;
        HttpServletResponse res = response;

        if( url != null ){
            try{
                request.getRequestDispatcher(url).forward(req, res);
            }catch(Exception ex){
                Archivo.guardarCadena("Error al redireccionar: "+ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    public static void irA(String pagina, HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Redireccionando...</title>");
        out.println("<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0;"+pagina+"\">");
        out.println("</head>");
        out.println("<body>");
        out.println("</body>");
        out.println("</html>");
    }
}
