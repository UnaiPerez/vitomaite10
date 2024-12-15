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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;


@WebServlet(name = "ServletBusquedaBasica", urlPatterns = {"/ServletBusquedaBasica"})
public class ServletBusquedaBasica extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Los parametros del formulario de la busqueda basica
        String gender = request.getParameter("gender");
        int ageMin = Integer.parseInt(request.getParameter("ageMin"));
        int ageMax = Integer.parseInt(request.getParameter("ageMax"));
        String city = request.getParameter("city");
        

        //conexion con la base de datos
       String query = "Select * FROM usuario WHERE (genero = ? OR ? = 'todos') and edad BETWEEN ? and ? and ciudad = ?";
        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query))
        {
            
            ps.setString(1, gender);
            ps.setString(2, gender);
            ps.setInt(3, ageMin);
            ps.setInt(4, ageMax);
            ps.setString(5, city);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<String> resultados = new ArrayList<>();
            while(rs.next()){
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String foto = rs.getString("foto");
                
                resultados.add("<div class='user-card'><img class='user-photo' src='"+ foto + "'alt= 'Foto usuario'><h3>"+ nombre +
                            ", "+ edad + " años</h3><a href='login.jsp'>Ver mas detalles</a></div>");
            }
            
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
            
        } catch (SQLException ex) {
           response.getWriter().println("Error en la base da datos: "+ ex.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Busqueda basica de usuarios, para usuarios no logueados";
    }// </editor-fold>

}
