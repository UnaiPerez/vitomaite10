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

@WebServlet(name = "ServletVerLikes", urlPatterns = {"/ServletVerLikes"})
public class ServletVerLikes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        session.removeAttribute("likes");
        
        String loggedEmail = (String) session.getAttribute("email");
        List<String[]> likes = new ArrayList<>();
        String query = "SELECT u.nombre, u.ciudad, u.email AS emailOrigen, u.edad, u.foto, "
                     + "EXISTS (SELECT 1 FROM meGusta m2 WHERE m2.emailOrigen = ? AND m2.emailDestino = u.email) AS reciproco "
                     + "FROM usuario u JOIN meGusta m ON u.email = m.emailOrigen "
                     + "WHERE m.emailDestino = ?";
        
        try(Connection conn = BD.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loggedEmail);
            ps.setString(2, loggedEmail);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                String nombre = rs.getString("nombre");
                String ciudad = rs.getString("ciudad");
                String email = rs.getString("emailOrigen");
                String edad = rs.getString("edad");
                String foto = rs.getString("foto");
                boolean reciproco = rs.getBoolean("reciproco");
                
                likes.add(new String[]{nombre, edad, ciudad, foto, email, String.valueOf(reciproco)});
            }
            
            session.setAttribute("likes", likes);
            request.getRequestDispatcher("verLikes.jsp").forward(request, response);
        } catch (SQLException e){
            response.getWriter().println("Error en la base de datos" + e.getMessage());
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
        return "Servlet para ver los likes que le han dado al usuario logueado.";
    }// </editor-fold>

}
