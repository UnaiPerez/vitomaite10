<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles del usuario</title>
        <link rel="website icon" type='png' href="uploads/fotosVitoMaite10/logo.png">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/detalles.css">
        <style>
            body {
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center;
            }
            #map {
                width: 100%;
                height: 300px; 
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        
        <%-- Header --%>
        <header class='header'>
            <img src="uploads/fotosVitoMaite10/logo.png" class="logo" alt='Logo de VitoMaite'>
            <h1 class='header-title'>VitoMaite</h1>
            <a href='resultadosMatching.jsp' class='btn-back'>Volver</a>
        </header>
        
        <%-- Main --%>
        <main class='main-content'>
            <div id='userDetails-container'>
                <% 
                    String nombre = (String) request.getAttribute("nombre");
                    String email = (String) request.getParameter("email");
                    int edad = (request.getAttribute("edad") != null) ? (int) request.getAttribute("edad") : 0;
                    String ciudad = (String) request.getAttribute("ciudad");
                    String genero = (String) request.getAttribute("genero");
                    String foto = (String) request.getAttribute("foto");
                    ArrayList<String> aficiones = (ArrayList<String>) request.getAttribute("aficiones");
                    boolean isLiked = (boolean) request.getAttribute("isLiked");
                    double latitud = (double) request.getAttribute("latitud");
                    double longitud = (double) request.getAttribute("longitud");
                %>
                
                <% if (!foto.startsWith("uploads/")) { %>
                                    <img src="data:image/jpeg;base64,<%= foto %>" alt="Foto de perfil" class="userPhoto">
                            <% } else { %>
                                    <img src="<%= foto %>" alt="Foto de perfil" class="userPhoto">
                            <% } %>
                <h2><%= nombre %></h2>
                <p><strong>Email:</strong> <%= email %></p>
                <p><strong>Ciudad:</strong> <%= ciudad %></p>
                <p><strong>Edad:</strong> <%= edad %></p>
                <p><strong>Género:</strong> <%= genero %></p>
                
                <div class='user-afi'>
                    <h3>Aficiones:</h3>
                    <ul>
                        <%
                            if (aficiones != null && !aficiones.isEmpty()) {
                                for (String aficion : aficiones) {
                                    out.println("<li>" + aficion + "</li>");
                                }
                            } else {
                                out.println("<p>Este usuario no ha añadido ninguna afición.</p>");
                            }
                        %>
                    </ul>
                </div>
                
                <%-- Mapa para la ubicación del usuario --%>
                <div id="map"></div>
            
            <%-- Botones --%>
            <div class='user-actions'>
                <% if (!isLiked) { %>
                <form action='ServletDetalles' method='post'>
                    <input type='hidden' name="action" value='like'>
                    <input type="hidden" name='email' value="<%= email %>">
                    <button type="submit" class="btn-like">ME GUSTA</button>
                </form>
                <% } else { %>
                <form action='ServletDetalles' method='post'>
                    <input type='hidden' name="action" value="unlike">
                    <input type="hidden" name='email' value="<%= email %>">
                    <button type="submit" class="btn-like">QUITAR ME GUSTA</button>
                </form>
                <% } %>
            </div>
        </div>
        </main>
        
        <%-- Footer --%>
        <footer class='footer'>
            <p>&copy; 2024 VitoMaite10 - Encuentra el amor</p>
        </footer>
        
        <%-- Script de Google Maps --%>
        <script>
            function initMap() {
                const lat = <%= latitud %>;
                const lng = <%= longitud %>;

                // Validar que las coordenadas sean válidas
                if (isNaN(lat) || isNaN(lng)) {
                    console.error("Latitud o longitud no válidas.");
                    return;
                }

                console.log("Latitud:", lat, "Longitud:", lng);

                const userLocation = { lat: lat, lng: lng };
                const map = new google.maps.Map(document.getElementById('map'), {
                    center: userLocation,
                    zoom: 14
                });

                new google.maps.Marker({
                    position: userLocation,
                    map: map,
                    title: 'Ubicación del usuario'
                });
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHpPv4eeZrBdR12bfQJl2xZoEsCKuhtoc&callback=initMap" async defer></script>
    </body>
</html>
