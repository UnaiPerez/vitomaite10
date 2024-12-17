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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gender = request.getParameter("gender");
        int ageMin = Integer.parseInt(request.getParameter("ageMin"));
        int ageMax = Integer.parseInt(request.getParameter("ageMax"));
        String city = request.getParameter("city");

        String query;

        if (gender == null || gender.equalsIgnoreCase("todos")) {
            query = "SELECT * FROM usuario WHERE edad BETWEEN ? AND ? AND ciudad = ?";
        } else {
            query = "SELECT * FROM usuario WHERE genero = ? AND edad BETWEEN ? AND ? AND ciudad = ?";
        }

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (gender == null || gender.equalsIgnoreCase("todos")) {
                ps.setInt(1, ageMin);
                ps.setInt(2, ageMax);
                ps.setString(3, city);
            } else {
                ps.setString(1, gender);
                ps.setInt(2, ageMin);
                ps.setInt(3, ageMax);
                ps.setString(4, city);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<String> resultados = new ArrayList<>();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String foto = rs.getString("foto");

                resultados.add("<div class='user-card'>" +
                        "<img class='user-photo' src='" + foto + "' alt='Foto usuario'>" +
                        "<h3>" + nombre + ", " + edad + " años</h3>" +
                        "<a href='login.jsp'>Ver más detalles</a></div>");
            }

            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);

        } catch (SQLException ex) {
            response.getWriter().println("Error en la base de datos: " + ex.getMessage());
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
        return "Servlet que realiza búsqueda básica.";
    }
}


