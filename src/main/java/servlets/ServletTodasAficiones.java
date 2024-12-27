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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.BD;

@WebServlet(name = "ServletTodasAficiones", urlPatterns = {"/ServletTodasAficiones"})
public class ServletTodasAficiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("ServletTodasAficiones ejecutándose");

        String query = "SELECT * FROM aficion";

        try (Connection conn = BD.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión exitosa a la base de datos");
            } else {
                System.out.println("No se pudo establecer conexión con la base de datos");
            }

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            List<String[]> aficiones = new ArrayList<>();

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
                aficiones.add(new String[]{id, nombre});
            }

            if (aficiones.isEmpty()) {
                System.out.println("No se encontraron aficiones en la base de datos");
            }

            request.setAttribute("aficiones", aficiones);
            request.getRequestDispatcher("busquedaAficiones.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error en la base de datos: " + e.getMessage());
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
        return "Servlet para cargar todas las aficiones";
    }
}

