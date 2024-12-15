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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/ServletLogin"})
public class ServletLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try(Connection conn = BD.getConnection()){
            String query = "SELECT * FROM usuario WHERE email = ? AND contraseña = ?";
            try(PreparedStatement ps = conn.prepareStatement(query)){
                ps.setString(1, email);
                ps.setString(2, password);
                
                try(ResultSet rs = ps.executeQuery()){
                
                    if(rs.next()){
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedInUser", rs.getString("nombre"));
                    session.setAttribute("loggedInEmail", email);
                    
                    //Redirigir a la pantalla del logueado
                    response.sendRedirect("pantallaLogueado.jsp");
                } else {
                    request.setAttribute("errorMessage", "Email o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                }
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Hubo un error al acceder a la base de datos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login de un usuario";
    }// </editor-fold>

}
