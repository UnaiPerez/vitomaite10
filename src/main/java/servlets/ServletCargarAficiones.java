
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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BD;


@WebServlet(name = "ServletCargarAficiones", urlPatterns = {"/ServletCargarAficiones"})
public class ServletCargarAficiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String loggedEmail = (String) session.getAttribute("email");
        String query = "SELECT * FROM aficion WHERE id NOT IN (SELECT idAficion FROM usuAfi WHERE emailUsuario = ?)";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, loggedEmail);
            ResultSet rs = ps.executeQuery();

            List<String[]> aficionesDisponibles = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                aficionesDisponibles.add( new String[]{id, nombre});
            }

            request.setAttribute("aficionesDisponibles", aficionesDisponibles);
            request.getRequestDispatcher("añadirAfi.jsp").forward(request, response);
        } catch (SQLException ex) {
            response.getWriter().println("Error en la base de datos: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletCargarAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletCargarAficiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

