
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver likes</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/verLikes.css">
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        <style>
            body{
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-size: cover;
                background-repeat: no-repeat;
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
            <div id="likes-container">
                <%
                    List<String[]> likes = (List<String[]>) session.getAttribute("likes");
                    if(likes != null && !likes.isEmpty()){
                        for(String[] like: likes){
                            String nombre = like[0];
                            String edad = like[1];
                            String ciudad = like[2];
                            String foto = like[3];
                            String email = like[4];
                            boolean reciproco = Boolean.parseBoolean(like[5]);
                            
                %>
                <div class="like-card">
                    <a href="ServletDetallesLikes?email=<%= email %>">
                        <div class="info">
                            <% if (!foto.startsWith("uploads/")) { %>
                                    <img src="data:image/jpeg;base64,<%= foto %>" alt="Foto de perfil" class="userPhoto">
                            <% } else { %>
                                    <img src="<%= foto %>" alt="Foto de perfil" class="userPhoto">
                            <% } %>
                            <h3><%= nombre %>, <%= edad %></h3>
                            <p><%= ciudad %></p>
                        </div>
                    </a>
                    <% if (reciproco) { %>
                    <a href="ServletDetallesLikes?email=<%= email %>">
                         <div class="heart">❤️</div>
                    </a>
                    <% } %>
                        </div>

                <%
                        }
                    } else {
                %>
                <p>No tienes likes aun.</p>
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
