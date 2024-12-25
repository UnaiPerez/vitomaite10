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
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletEliminarAficiones", urlPatterns = {"/ServletEliminarAficiones"})
public class ServletEliminarAficiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String email = (String) session.getAttribute("email");
        String[] aficionesSeleccionadas = request.getParameterValues("aficiones");
        String query = "DELETE FROM usuAfi WHERE emailUsuario = ? AND idAficion = ?";
        
        if(aficionesSeleccionadas == null || aficionesSeleccionadas.length == 0){
            request.setAttribute("mensaje", "No seleccionaste ninguna aficion para eliminar.");
            request.getRequestDispatcher("eliminarAfi.jsp").forward(request, response);
            return;
        }
        
        try(Connection conn = BD.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            
            for(String idAficion : aficionesSeleccionadas){
                
            ps.setString(1, email);
            ps.setInt(2, parseInt(idAficion));
            ps.addBatch();
            }
           
            ps.executeBatch();
            request.setAttribute("mensaje", "Las aficiones seleccionadas han sido eliminadas");
            request.getRequestDispatcher("ServletCargarAficionesUsuario").forward(request, response);
        } catch (SQLException e){
             response.getWriter().println("Error en la base de datos: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletEliminarAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletEliminarAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
