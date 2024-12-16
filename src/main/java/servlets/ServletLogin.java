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
        
        String queryEmailCheck = "SELECT * FROM usuario WHERE email = ?";
        String queryLoginCheck = "SELECT * FROM usuario where email = ? AND contraseña = ?";
        
            
            try(Connection conn = BD.getConnection();
                PreparedStatement ps = conn.prepareStatement(queryEmailCheck);
                PreparedStatement ps2 = conn.prepareStatement(queryLoginCheck)
                    ){
                ps.setString(1, email);
                ResultSet rsEmailCheck = ps.executeQuery();
                if(!rsEmailCheck.next()){
                    //En el caso de que no exista el email
                    request.setAttribute("errorMessage", "No existe el email");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                //verificar la contraseña y email
                ps2.setString(1, email);
                ps2.setString(2, password);
                
                ResultSet rsLoginCheck = ps2.executeQuery();
                    if(!rsLoginCheck.next()){
                          request.setAttribute("errorMessage", "Contraseña incorrecta");
                          request.getRequestDispatcher("login.jsp").forward(request, response);
                          return;
                }
                //login existoso
                String nombre = rsLoginCheck.getString("nombre");
                request.getSession().setAttribute("loggedInUser", nombre);
                response.sendRedirect("pantallaLogueado.jsp");
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Hubo un error al acceder a la base de datos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            ex.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Login de un usuario";
    }// </editor-fold>

}
