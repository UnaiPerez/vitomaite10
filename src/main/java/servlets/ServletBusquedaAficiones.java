/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletBusquedaAficiones", urlPatterns = {"/ServletBusquedaAficiones"})
public class ServletBusquedaAficiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Limpiar resultados previos de la sesión
        session.removeAttribute("resultados");
            
        if(session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String loggedEmail = (String) session.getAttribute("email");
        String[] aficionesSeleccionadas = request.getParameterValues("aficiones");
        
        if(aficionesSeleccionadas == null || aficionesSeleccionadas.length == 0){
            request.setAttribute("mensaje", "Seleccione al menos una aficion");
            request.getRequestDispatcher("ServletTodasAficiones").forward(request, response);
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT distinct u.* ")
                .append("FROM usuario u ")
                .append("JOIN usuAfi ua on u.email = ua.emailUsuario ")
                .append("WHERE u.email != ? and (");
        
        for(int i = 0; i < aficionesSeleccionadas.length; i++){
            query.append("ua.idAficion = ?");
            if(i < aficionesSeleccionadas.length - 1){
                query.append(" OR ");
            }
        }
        query.append(")");
        
        try(Connection conn = BD.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString())){
            
            ps.setString(1, loggedEmail);
            for(int i = 0; i < aficionesSeleccionadas.length; i++){
                ps.setInt(i + 2, Integer.parseInt(aficionesSeleccionadas[i]));
            }
            
            ResultSet rs = ps.executeQuery();
            ArrayList<String> resultados = new ArrayList<>();
            
            while(rs.next()){
                    String nombre = rs.getString("nombre");
                    String foto = rs.getString("foto");
                    String ciudad = rs.getString("ciudad");
                    String email = rs.getString("email");
                    int edad = rs.getInt("edad");
                    
                    resultados.add("<div class='user-card'>"
                            + "<img class='user-photo' src='"+foto+"' alt='foto del usuario'>"
                            + "<h3>" + nombre + ", "+edad+"</h3>"
                            + "<h4>"+ ciudad + "</h4>"
                            + "<a href='ServletDetalles?email=" + email +"'>Ver mas detalles</a></div>");
                }
                
                session.setAttribute("resultados", resultados);
                response.sendRedirect("resultadosLogueado.jsp");
                
        } catch (SQLException e){
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la busqueda por aficiones";
    }// </editor-fold>

}
