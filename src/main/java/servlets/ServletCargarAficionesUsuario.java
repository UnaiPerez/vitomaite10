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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;

@WebServlet(name = "ServletCargarAficionesUsuario", urlPatterns = {"/ServletCargarAficionesUsuario"})
public class ServletCargarAficionesUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(false);
        if(session == null ||session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String email = (String) session.getAttribute("email");
       String query = "SELECT * FROM aficion WHERE id IN (SELECT idAficion FROM usuAfi WHERE emailUsuario = ?)";
        
        try(Connection conn = BD.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            List<String[]> aficionesUsuario = new ArrayList<>();
            while(rs.next()){
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                aficionesUsuario.add( new String[]{id, nombre});
            }
            
            request.setAttribute("aficionesUsuario", aficionesUsuario);
            request.getRequestDispatcher("eliminarAfi.jsp").forward(request, response);
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
            Logger.getLogger(ServletCargarAficionesUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletCargarAficionesUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
