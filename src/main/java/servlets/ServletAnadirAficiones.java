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
@WebServlet(name = "ServletAnadirAficiones", urlPatterns = {"/ServletAnadirAficiones"})
public class ServletAnadirAficiones extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("email") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String emailUsu = (String) session.getAttribute("email");
           String[] aficionesSeleccionadas = request.getParameterValues("aficiones");

        if (aficionesSeleccionadas == null || aficionesSeleccionadas.length == 0) {
            request.setAttribute("mensaje", "No se seleccionaron aficiones.");
            request.getRequestDispatcher("ServletCargarAficiones").forward(request, response);
            return;
        }
        try(Connection conn = BD.getConnection()){


                String query = "INSERT INTO usuAfi (emailUsuario, idAficion) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    for (String id : aficionesSeleccionadas) {
                        ps.setString(1, emailUsu);
                        ps.setInt(2, Integer.parseInt(id));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                
                 request.setAttribute("mensaje", "Aficiones añadidas correctamente.");
                 request.getRequestDispatcher("ServletCargarAficiones").forward(request, response);
            }
        } catch (SQLException e){
            e.printStackTrace();
            response.sendRedirect("pantallaLogueado.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAnadirAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAnadirAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
