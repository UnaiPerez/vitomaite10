
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver aficiones</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/verAfi.css">
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
        <header class='header'>
            <img src="uploads/fotosVitoMaite10/logo.png" class='logo' alt='logo de VitoMaite'>
            <h1 class='header-title'>VitoMaite</h1>
            <a href="pantallaLogueado.jsp" class='btn-back'>Volver</a>
        </header>
        
        <main class='main-content'>
            <h2>Tus aficiones:</h2>
            <div id="aficiones-container">
                <%
                    List<String> aficionesUsuario = (List<String>) request.getAttribute("aficionesUsuario");
                    if(aficionesUsuario != null && !aficionesUsuario.isEmpty()){
                        for(String aficion: aficionesUsuario){
                            
                %>
                <div class='aficion-card'><%= aficion %></div>
                
                <%
                    }
                    } else {
                %>
                <p>No tienes ninguna aficion añadida.</p>
                <%
                    }
                %>
            </div> 
        </main>
            
            <footer class='footer'>
                <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>
            </footer>
    </body>
</html>
