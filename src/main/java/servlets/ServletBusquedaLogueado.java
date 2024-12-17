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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletBusquedaLogueado", urlPatterns = {"/ServletBusquedaLogueado"})
public class ServletBusquedaLogueado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
            HttpSession session = request.getSession(false);
            String loggedEmail = (String) session.getAttribute("email");

            String gender = request.getParameter("gender");
            int ageMin = Integer.parseInt(request.getParameter("ageMin"));
            int ageMax = Integer.parseInt(request.getParameter("ageMax"));
            String city = request.getParameter("city");

            String query = "SELECT * FROM usuario WHERE (genero = ? or ? = 'todos') AND edad BETWEEN ? AND ? "
                            + "AND ciudad = ? AND email != ?";

            try(Connection con = BD.getConnection();
                PreparedStatement ps = con.prepareStatement(query);){
                
                
                ps.setString(1, gender);
                ps.setString(2, gender);
                ps.setInt(3, ageMin);
                ps.setInt(4, ageMax);
                ps.setString(5, city);
                ps.setString(6, loggedEmail);
                
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
                
                request.setAttribute("resultados", resultados);
                request.getRequestDispatcher("resultadosLogueado.jsp").forward(request, response);
                
        } catch(SQLException ex){
            response.getWriter().println("Error en la base de datos: " + ex.getMessage());
        }     
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletBusquedaLogueado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletBusquedaLogueado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
