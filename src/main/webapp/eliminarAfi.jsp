
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eliminar aficiones</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/eliminarAfi.css">
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        <style>
            body{
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }
        </style>
    </head>
    <body>
        <header class='header'>
            <img src="uploads/fotosVitoMaite10/logo.png" class='logo'>
            <h1 class='header-title'>VitoMaite</h1>
            <a href="pantallaLogueado.jsp" class='btn-back'>Volver</a>
        </header>
        
        <main class='main-content'>
            <h2>Seleccione las aficiones que quiere eliminar</h2>
            
            <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <div class="mensaje"><%= mensaje %></div>
        <%
            }
        %>
        
            <form id="form-del-aficiones" action="ServletEliminarAficiones" method='post'>
                <div id="aficiones-container">
                    <%
                        List<String[]> aficiones = (List<String[]>) request.getAttribute("aficionesUsuario");
                        if(aficiones != null && !aficiones.isEmpty()){
                            for (String[] aficion : aficiones){
                                String id = aficion[0];
                                String nombre = aficion[1];
                    %>
                    <div>
                        <label>
                            <input type='checkbox' name='aficiones' value="<%= id %>"> <%= nombre %>
                        </label>
                    </div>
                    <%
                        }
                        } else { 
                    %>
                    <p>No hay aficiones disponibles para eliminar</p>
                    <%
                        }
                    %>
                </div>
                <button type="submit" class='btn-action'>Eliminar aficion/es</button>
            </form>
        </main>
                
        <footer class='footer'>
            <p>&copy; 2024 VitoMaite10 - Encuentra el amor</p>          
        </footer>
    </body>
</html>
