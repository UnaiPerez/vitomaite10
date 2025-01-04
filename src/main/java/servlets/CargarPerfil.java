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
@WebServlet(name = "CargarPerfil", urlPatterns = {"/CargarPerfil"})
public class CargarPerfil extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String email = (String) session.getAttribute("email");
        //Lista de todas las ciudades
        List<String> ciudades = List.of("Vitoria", "Bilbao", "Donosti");
        
        try(Connection conn = BD.getConnection()){
            String query = "SELECT * FROM usuario WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String nombre = rs.getString("nombre");
                String ciudadActual = rs.getString("ciudad");
                int edad = rs.getInt("edad");
                double latitud = rs.getDouble("latitud");
                double longitud = rs.getDouble("longitud");
                
                //Eliminamos de la lista de ciudades la actual
                List<String> ciudadesDisponibles = new ArrayList<>(ciudades);
                ciudadesDisponibles.remove(ciudadActual);
                
                request.setAttribute("nombre", nombre);
                request.setAttribute("ciudad", ciudadActual);
                request.setAttribute("edad", edad);
                request.setAttribute("latitud", latitud);
                request.setAttribute("longitud", longitud);
                request.setAttribute("ciudades", ciudadesDisponibles);
                
                request.getRequestDispatcher("modificarPerfil.jsp").forward(request, response);
                
            }
            
        } catch (SQLException e){
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
        return "Servlet para cargar el perfil actual";
    }// </editor-fold>

}
