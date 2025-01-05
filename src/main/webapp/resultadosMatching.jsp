
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados del matching</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/resultadosMatch.css">
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
            <img src="uploads/fotosVitoMaite10/logo.png" class="logo">
            <h1 class="header-title">VitoMaite</h1>
            <a href="pantallaLogueado.jsp" class="btn-back">Volver</a>
        </header>
        
        <main class="main-content">
            <div class="users-container">
                <%
                    List<Map<String, String>> matchedUsers = (List<Map<String, String>>) session.getAttribute("matchedUsers");
                    if(matchedUsers != null && !matchedUsers.isEmpty()){
                        for(Map<String, String> user : matchedUsers){
                        String nombre = user.get("nombre");
                        String email = user.get("email");
                        String foto = user.get("foto");
                        String ciudad = user.get("ciudad");
                        String edad = user.get("edad");
                    
                        if(!foto.startsWith("uploads/")){
                %>
                <div class="user-card">
                    <img class='user-photo' src='data:image/jpeg;base64," + foto + "' alt='Foto usuario'>
                    <h3><%= nombre %>, <%= edad %> años</h3>
                    <p>Ciudad: <%= ciudad %></p>
                    <a href="detallesMatching?email=<%= email %>" class="btn-view-profile">Ver Perfil</a>
                </div>
                <%
                    } else{
                %>
                <div class="user-card">
                    <img src="<%= foto %>" alt="Foto de <%= nombre %>" class="user-photo">
                    <h3><%= nombre %>, <%= edad %> años</h3>
                    <h5>Ciudad: <%= ciudad %></h5>
                    <a href="detallesMatching?email=<%= email %>" class="btn-view-profile">Ver Perfil</a>
                </div>
                <%
                }
                    }
                    } else {
                %>
                <p>No se encontraron usuarios que coincidan con tus preferencias.</p>
                <%
                    }
                %>
            </div>
        </main>
            
            <footer class="footer">
                <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>
            </footer>
    </body>
</html>
