/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import utils.BD;

@WebServlet(name = "ServletModificarPerfil", urlPatterns = {"/ServletModificarPerfil"})
@MultipartConfig
public class ServletModificarPerfil extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("email");
        String nuevaCiudad = request.getParameter("city");
        String nuevaEdadStr = request.getParameter("age");
        String nuevaLatitudStr = request.getParameter("latitud");
        String nuevaLongitudStr = request.getParameter("longitud");
        Part nuevaFoto = request.getPart("photo");

        Integer nuevaEdad = null;
        Double nuevaLatitud = null, nuevaLongitud = null;
        String nuevaFotoBase64 = null;

        try {
            // Validar y convertir los campos opcionales
            if (nuevaEdadStr != null && !nuevaEdadStr.isEmpty()) {
                nuevaEdad = Integer.parseInt(nuevaEdadStr);
            }

            if (nuevaLatitudStr != null && !nuevaLatitudStr.isEmpty()) {
                nuevaLatitud = Double.parseDouble(nuevaLatitudStr);
            }

            if (nuevaLongitudStr != null && !nuevaLongitudStr.isEmpty()) {
                nuevaLongitud = Double.parseDouble(nuevaLongitudStr);
            }

            if (nuevaFoto != null && nuevaFoto.getSize() > 0) {
                try (InputStream fotoInputStream = nuevaFoto.getInputStream()) {
                    byte[] fotoBytes = fotoInputStream.readAllBytes();
                    nuevaFotoBase64 = Base64.getEncoder().encodeToString(fotoBytes);
                }
            }

            // Construir la consulta SQL dinámicamente
            StringBuilder query = new StringBuilder("UPDATE usuario SET ");
            List<Object> params = new ArrayList<>();

            if (nuevaCiudad != null && !nuevaCiudad.isEmpty()) {
                query.append("ciudad = ?, ");
                params.add(nuevaCiudad);
            }
            if (nuevaEdad != null) {
                query.append("edad = ?, ");
                params.add(nuevaEdad);
            }
            if (nuevaLatitud != null) {
                query.append("latitud = ?, ");
                params.add(nuevaLatitud);
            }
            if (nuevaLongitud != null) {
                query.append("longitud = ?, ");
                params.add(nuevaLongitud);
            }
            if (nuevaFotoBase64 != null) {
                query.append("foto = ?, ");
                session.setAttribute("foto", nuevaFotoBase64);
                params.add(nuevaFotoBase64);
            }

            // Eliminar la última coma y espacio, agregar cláusula WHERE
            if (params.isEmpty()) {
                request.setAttribute("errorMessage", "No se proporcionaron datos para actualizar.");
                request.getRequestDispatcher("modificarPerfil.jsp").forward(request, response);
                return;
            }

            query.setLength(query.length() - 2); // Quitar ", "
            query.append(" WHERE email = ?");
            params.add(email);

            // Ejecutar la consulta SQL
            try (Connection conn = BD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    request.setAttribute("successMessage", "Perfil actualizado con éxito.");
                } else {
                    request.setAttribute("errorMessage", "No se pudo actualizar el perfil.");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Formato numérico inválido en edad, latitud o longitud.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al actualizar el perfil en la base de datos.");
        }

        request.getRequestDispatcher("modificarPerfil.jsp").forward(request, response);
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
        return "Servlet para modificar el perfil";
    }// </editor-fold>

}
