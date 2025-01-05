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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.BD;

@WebServlet(name = "ServletMatching", urlPatterns = {"/ServletMatching"})
public class ServletMatching extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Limpiar resultados previos de la sesión
            session.removeAttribute("matchedUsers");
            
            
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String loggedEmail = (String) session.getAttribute("email");
        
        try(Connection conn = BD.getConnection()){
            
            //datos del logueado
            String queryUsu = "SELECT * FROM usuario WHERE email = ?";
            PreparedStatement psUsu = conn.prepareStatement(queryUsu);
            psUsu.setString(1, loggedEmail);
            ResultSet rs = psUsu.executeQuery();
            
            if(!rs.next()){
                response.sendRedirect("pantallaLogueado.jsp");
                return;
            }
            
            String genero = rs.getString("genero");
            double latitud = rs.getDouble("latitud");
            double longitud = rs.getDouble("longitud");
            int edad = rs.getInt("edad");
            
            String queryMatch = """
                SELECT DISTINCT u.nombre, u.email, u.foto, u.ciudad, u.edad, u.latitud, u.longitud
                FROM usuario u
                JOIN usuAfi ua1 ON u.email = ua1.emailUsuario
                JOIN usuAfi ua2 ON ua1.idAficion = ua2.idAficion
                WHERE ua2.emailUsuario = ?
                  AND u.email != ?
                  AND ST_Distance_Sphere(POINT(u.longitud, u.latitud), POINT(?, ?)) <= 5000
                  AND ABS(u.edad - ?) <= 5
                  AND u.genero != ?
            """;
            
            PreparedStatement psMatch = conn.prepareStatement(queryMatch);
            psMatch.setString(1, loggedEmail);
            psMatch.setString(2, loggedEmail);
            psMatch.setDouble(3, longitud);
            psMatch.setDouble(4, latitud);
            psMatch.setInt(5, edad);
            psMatch.setString(6, genero);
            
            ResultSet rsMatch = psMatch.executeQuery();
            List<Map<String, String>> matchedUsers = new ArrayList<>();
            while (rsMatch.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("nombre", rsMatch.getString("nombre"));
                user.put("email", rsMatch.getString("email"));
                user.put("foto", rsMatch.getString("foto"));
                user.put("ciudad", rsMatch.getString("ciudad"));
                user.put("edad", rsMatch.getString("edad"));
                matchedUsers.add(user);
            }
            
            session.setAttribute("matchedUsers", matchedUsers);
            request.getRequestDispatcher("resultadosMatching.jsp").forward(request, response);
            
        } catch (SQLException e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos");
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
        return "Servlet para el matching";
    }// </editor-fold>

}
