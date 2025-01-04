/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import jakarta.json.Json;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.BD;


/**
 *
 * @author megan
 */
@WebServlet(name = "ServletBusquedaGeolocalizacion", urlPatterns = {"/ServletBusquedaGeolocalizacion"})
public class ServletBusquedaGeolocalizacion extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String latitudStr = request.getParameter("latitud");
        String longitudStr = request.getParameter("longitud");
        String radioStr = request.getParameter("radio");
        
        if(latitudStr == null || longitudStr == null || radioStr == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parametros");
            return;
        }
        
        double latitud = Double.parseDouble(latitudStr);
        double longitud = Double.parseDouble(longitudStr);
        double radio = Double.parseDouble(radioStr);
        
        try(Connection conn = BD.getConnection()){
             String query = """
                SELECT nombre, edad, latitud, longitud
                FROM usuario
                WHERE ST_Distance_Sphere(
                    POINT(longitud, latitud), 
                    POINT(?, ?)
                ) <= ? * 1000
            """;
             
             PreparedStatement ps = conn.prepareStatement(query);
             ps.setDouble(1, longitud);
             ps.setDouble(2, latitud);
             ps.setDouble(3, radio);
             ResultSet rs = ps.executeQuery();
             
             List<Map<String, Object>> usuarios = new ArrayList<>();
             while(rs.next()){
                 Map<String, Object> usuario = new HashMap<>();
                 usuario.put("nombre", rs.getString("nombre"));
                 usuario.put("edad", rs.getInt("edad"));
                 usuario.put("latitud", rs.getDouble("latitud"));
                 usuario.put("longitud", rs.getDouble("longitud"));
                 usuarios.add(usuario);
             }
             
             response.setContentType("application/json");
             response.getWriter().write(new Gson().toJson(usuarios));
             
        } catch (SQLException e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la base de datos.");
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
        return "Servlet para busqueda de geolocalizacion.";
    }// </editor-fold>
}
