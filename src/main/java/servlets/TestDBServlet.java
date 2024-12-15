/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.BD;

@WebServlet(name = "TestDBServlet", urlPatterns = {"/testdb"})
public class TestDBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Connection conn = BD.getConnection();

            if (conn != null) {
                out.println("<h1>Conexión a la base de datos exitosa</h1>");
                out.println("<h2>Usuarios en la tabla:</h2>");
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    out.println("<p>Nombre: " + rs.getString("nombre") + ", Email: " + rs.getString("email") + "</p>");
                }
            } else {
                out.println("<h1>Error: No se pudo conectar a la base de datos</h1>");
            }
        } catch (SQLException e) {
            response.getWriter().println("<h1>Error al ejecutar la consulta: " + e.getMessage() + "</h1>");
        }
    }
}

