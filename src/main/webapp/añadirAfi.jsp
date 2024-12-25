
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Añadir Aficiones</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/añadirAfi.css">
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        
        <style>
            body{
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center;
            }
        </style>
    </head>
    <body>
        <header class="header">
            <img src="uploads/fotosVitoMaite10/logo.png" class="logo" alt="Logo de vitoMaite">
            <h1 class="header-title">VitoMaite</h1>
            <a class="btn-back" href="pantallaLogueado.jsp">Volver</a>
        </header>
        
        
        <main class="main-content">
                <h2>Seleccione las aficiones que le gusten</h2>
                
                 <%-- Mostrar mensaje de éxito o error --%>
        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <div class="mensaje"><%= mensaje %></div>
        <%
            }
        %>
                <form id="form-add-aficiones" method="POST" action="ServletAnadirAficiones">

                    <div id="aficiones-container">
                        <%-- Las aficiones seran cargadas desde el servidor --%>
                        <%
                            List<String[]> aficiones = (List<String[]>) request.getAttribute("aficionesDisponibles");
                            if(aficiones != null && !aficiones.isEmpty()){
                                for(String[] aficion : aficiones){
                                    String id = aficion[0];
                                    String nombre = aficion[1];
                        %>
                        <div>
                            <label>
                                <input type="checkbox" name="aficiones" value="<%= id %>"><%= nombre %>
                            </label>
                        </div>
                        <%
                            }
                            } else{ 
                        %>
                        <p> No hay aficiones disponibles para añadir</p>
                        <%
                            }
                        %>
                    </div>
                    <button type="submit" class="btn-action">Añadir aficion/es</button>
                </form>
        </main>
                    
        <footer class="footer">
            <p>&copy; 2024 VitoMaite10 - Encuentra el amor</p>             
        </footer>
        
    </body>
</html>
