
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar perfil</title>
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/modificarPerfil.css">
        <style>
            body {
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-position: center center;
                background-repeat: no-repeat;
                background-size: cover;
            }
        </style>
    </head>
    <body>
        <header class="header">
            <img src="uploads/fotosVitoMaite10/logo.png" class="logo">
            <h1 class="header-title">VitoMaite</h1>
            <a href="pantallaLogueado.jsp" class="btn-back">Volver</a>
        </header>
        
        <main class="profile-container">
            <div>
                <h2>Modifica tu perfil:</h2>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    String successMessage = (String) request.getAttribute("successMessage");
                    if(errorMessage != null){
                %>
                <div class="mensaje"><%=errorMessage%></div>
                <% } else if(successMessage != null) {  
                %>
                <div class="mensaje"><%=successMessage%></div>
                <%
                    }                   
                %>
                <form id="profile-form" action="ServletModificarPerfil" method="post" enctype="multipart/form-data">

                <label for="photo">Nueva foto de perfil:</label>
                <input type="file" name="photo" id="photo" accept="image/*">
                
                <label for="age">Edad:</label>
                <input type="number" name="age" id="age" min="18" max="100">
                
                <label for="city">Ciudad:</label>
    <select name="city" id="city">
        <option value="" disabled selected>Selecciona una ciudad:</option>
        <%
            List<String> ciudades = (List<String>) request.getAttribute("ciudades");
            if (ciudades != null) {
                for (String ciudad : ciudades) {
        %>
        <option value="<%= ciudad %>"><%= ciudad %></option>
        <%
                }
            }
        %>
    </select>
                
                <label for="latitud">Latitud:</label>
                <input type="number" step="any" name="latitud" id="latitud">
                
                <label for="longitud">Longitud:</label>
                <input type="number" step="any" name="longitud" id="longitud">
                
                <div class="form-buttons">
                    <button type="submit" class="btn-accept">Aceptar</button>
                    <a href="pantallaLogueado.jsp" class="btn-cancel">Cancelar</a>
                </div>
                </form>
            </div>
        </main>
                
                <footer class="footer">
                    <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>
                </footer>
    </body>
</html>
