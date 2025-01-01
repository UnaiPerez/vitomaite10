
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - VitoMaite</title>
    <link rel="stylesheet" href="css/login.css">
    <link rel="website icon" type='png' href="uploads/fotosVitoMaite10/logo.png">
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
    <!-- Encabezado -->
    <header class="header">
            <img src='uploads/fotosVitoMaite10/logo.png' alt="Logo de VitoMaite10" class='logo'>
            <h1 class='header-title'>VitoMaite</h1>
            <a href="index.jsp" class="btn-back">Volver</a>
    </header>

    <!-- Formulario de Login -->
    <main class="login-container">
        <div class="login-box">
            <h2>Iniciar Sesión</h2>
            <%-- Mostrar mensaje de error si existe--%>
            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if(errorMessage != null){
             %>
             <div class='errorMessage'>
                 <%=errorMessage %>
             </div>
             <% } %>
            
            <form id="login-form" action='ServletLogin' method='post'>
                <div class="form-group">
                    <label for="email">Correo Electrónico</label>
                    <input type="email" id="email" name='email' class="input-field" placeholder="ejemplo@correo.com" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name='password' class="input-field" placeholder="Introduce tu contraseña" required>
                </div>
                
                <button type="submit" class="btn-login">Iniciar Sesión</button>
            </form>
            <p class="register-link">¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>
        </div>
    </main>

    <!-- Pie de Página -->
    <footer class="footer">
        <p>&copy; 2024 VitoMaite10 - Encuentra tu pareja ideal</p>
    </footer>

</body>
</html>
