/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {

    private static Connection conn;

    public static Connection getConnection() throws SQLException{
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vitomaite10", "root", ".Nala2004");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Error al cargar el driver de MySQL ", e);
            }
        }
        return conn;
    }
}

