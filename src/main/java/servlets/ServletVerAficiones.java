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
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletVerAficiones", urlPatterns = {"/ServletVerAficiones"})
public class ServletVerAficiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String loggedEmail = (String) session.getAttribute("email");
        String query = "SELECT nombre "
                + "FROM aficion JOIN usuAfi on id = idAficion "
                + "WHERE emailUsuario = ?";
        List<String> aficionesUsuario = new ArrayList<>();
        
        try(Connection conn = BD.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loggedEmail);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String nombre = rs.getString("nombre");
                aficionesUsuario.add(nombre);
            }
            
            request.setAttribute("aficionesUsuario", aficionesUsuario);
            request.getRequestDispatcher("verAfi.jsp").forward(request, response);
            
        } catch(SQLException e){
            request.setAttribute("errorMensaje", "Error al cargar las aficiones: "+ e.getMessage());
            e.printStackTrace();
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
        return "Servlet para ver aficiones.";
    }// </editor-fold>

}
