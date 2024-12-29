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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;

/**
 *
 * @author megan
 */
@WebServlet(name = "ServletDetallesLikes", urlPatterns = {"/ServletDetallesLikes"})
public class ServletDetallesLikes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
          
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String loggedEmail = (String) session.getAttribute("email");
        String selectedEmail = request.getParameter("email");
        String action = request.getParameter("action");
        
        if(selectedEmail == null || selectedEmail.isEmpty()){
            response.sendRedirect("pantallaLogueado.jsp");
            return;
        }
        
        try(Connection conn = BD.getConnection()){
            if(action == null || action.isEmpty()){
                
                String query = "SELECT * FROM usuario WHERE email = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, selectedEmail);
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    String nombre = rs.getString("nombre");
                    String ciudad = rs.getString("ciudad");
                    String foto = rs.getString("foto");
                    int edad = rs.getInt("edad");
                    String genero = rs.getString("genero");
                    double latitud = rs.getDouble("latitud");
                    double longitud = rs.getDouble("longitud");
                    
                    request.setAttribute("nombre", nombre);
                    request.setAttribute("ciudad", ciudad);
                    request.setAttribute("foto", foto);
                    request.setAttribute("edad", edad);
                    request.setAttribute("genero", genero);
                    request.setAttribute("latitud", latitud);
                    request.setAttribute("longitud", longitud);
                }
                
                //Aficiones del usuario seleccionado para ver mas detalles
                String queryAfi = "SELECT afi.nombre "
                        + "FROM aficion afi JOIN usuAfi ua ON afi.id = ua.idAficion "
                        + "WHERE ua.emailUsuario = ?";
                
                PreparedStatement psAfi = conn.prepareStatement(queryAfi);
                psAfi.setString(1, selectedEmail);
                ResultSet rsAfi = psAfi.executeQuery();
                
                ArrayList<String> aficiones = new ArrayList<>();
                while(rsAfi.next()){
                    String nombreAfi = rsAfi.getString("nombre");
                    aficiones.add(nombreAfi);
                }
                
                request.setAttribute("aficiones", aficiones);
                
                //Comprobar si el usuario logueado le ha dado me gusta
                String queryLike = "SELECT * FROM meGusta WHERE emailOrigen = ? AND emailDestino =?";
                PreparedStatement psLike = conn.prepareStatement(queryLike);
                psLike.setString(1, loggedEmail);
                psLike.setString(2, selectedEmail);
                
                ResultSet rsLike = psLike.executeQuery();
                boolean isLiked = rsLike.next();
                request.setAttribute("isLiked", isLiked);
                
                request.getRequestDispatcher("detallesLike.jsp").forward(request, response);
                
            } else if(action.equals("like")){
                
                String query = "INSERT INTO meGusta (emailOrigen, emailDestino) "
                        + "VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, loggedEmail);
                ps.setString(2, selectedEmail);
                ps.executeUpdate();
                
                response.sendRedirect("ServletDetallesLikes?email=" + selectedEmail);
                
            } else if (action.equals("unlike")){
                
                String query = "DELETE FROM meGusta WHERE emailOrigen = ? AND emailDestino = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                
                ps.setString(1, loggedEmail);
                ps.setString(2, selectedEmail);
                ps.executeUpdate();    
                
                response.sendRedirect("ServletDetallesLikes?email=" + selectedEmail);
                
            } else{
                response.sendRedirect("pantallaLogueado.jsp");
            }
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletDetalles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletDetalles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet detalles del usuario";
    }// </editor-fold>

}
