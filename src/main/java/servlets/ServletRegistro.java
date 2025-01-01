
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import utils.BD;

@WebServlet(name = "ServletRegistro", urlPatterns = {"/ServletRegistro"})
@MultipartConfig
public class ServletRegistro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        System.out.println("Recibiendo datos:");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String city = request.getParameter("city");
        String gender = request.getParameter("gender");
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");
        String latitudStr = request.getParameter("latitud");
        String longitudStr = request.getParameter("longitud");
        Part photo = request.getPart("photo");
        
         // Imprimir en la consola para verificar
         
    System.out.println("email: " + email);
    System.out.println("password: " + password);
    System.out.println("city: " + city);
    System.out.println("gender: " + gender);
    System.out.println("name: " + name);
    System.out.println("ageStr: " + ageStr);
    System.out.println("latitudStr: " + latitudStr);
    System.out.println("longitudStr: " + longitudStr);
    System.out.println("photo: " + (photo != null ? "Present" : "Null"));
    

        if (email == null || password == null || city == null || gender == null || name == null ||
                ageStr == null || latitudStr == null || longitudStr == null ||
                email.isEmpty() || password.isEmpty() || city.isEmpty() || gender.isEmpty() ||
                name.isEmpty() || ageStr.isEmpty() || latitudStr.isEmpty() || longitudStr.isEmpty()) {
            request.setAttribute("errorMessage", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        int age;
        double latitud, longitud;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 18 || age > 100) {
                request.setAttribute("errorMessage", "La edad debe estar entre 18 y 100 años.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }
            latitud = Double.parseDouble(latitudStr);
            longitud = Double.parseDouble(longitudStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Edad, latitud y longitud deben ser valores válidos.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        
        if (photo == null || photo.getSize() == 0) {
            request.setAttribute("errorMessage", "Debes subir una foto de perfil.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        String queryCheckEmail = "SELECT * FROM usuario WHERE email = ?";
        String queryInsertUser = "INSERT INTO usuario (email, contraseña, nombre, ciudad, edad, genero, latitud, longitud, foto) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = BD.getConnection()) {
            try (PreparedStatement psCheck = conn.prepareStatement(queryCheckEmail)) {
                psCheck.setString(1, email);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    request.setAttribute("errorMessage", "El correo ya está registrado.");
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                    return;
                }
            }

            String photoBase64;
            try (InputStream photoInput = photo.getInputStream()) {
                byte[] photoBytes = photoInput.readAllBytes();
                photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            }

            try (PreparedStatement psInsert = conn.prepareStatement(queryInsertUser)) {
                psInsert.setString(1, email);
                psInsert.setString(2, password);
                psInsert.setString(3, name);
                psInsert.setString(4, city);
                psInsert.setInt(5, age);
                psInsert.setString(6, gender);
                psInsert.setDouble(7, latitud);
                psInsert.setDouble(8, longitud);
                psInsert.setString(9, photoBase64);

                psInsert.executeUpdate();
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al registrar al usuario.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para el registro de usuarios.";
    }
}
