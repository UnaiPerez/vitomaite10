
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Busqueda por geolocalizacion</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/busquedaGeo.css">
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        <style>
            #map{
                width: 100%;
                height: 500px;
                margin-top: 20px;
            }
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
            <div>
                <label for="radius">Selecciona el radio(km):</label>
                <input type="range" id="radius" min="0.5" max="5" step="0.5" value="1">
                <span id="radius-value">1 km</span>
            </div>
            <div id="map"></div>
        </main>
        
        <footer class="footer">
            <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>   
        </footer>
        
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHpPv4eeZrBdR12bfQJl2xZoEsCKuhtoc" defer></script>
        <script src="js/busquedaGeo.js"></script>
    </body>
</html>
