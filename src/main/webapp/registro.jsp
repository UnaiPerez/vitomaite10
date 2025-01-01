
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro del usuario</title>
        <link rel="stylesheet" href="css/register.css">
        <link rel="website icon" href="uploads/fotosVitoMaite10/logo.png" type="png">
        <style>
            body{
                background-image: url('uploads/fotosVitoMaite10/vitoMaiteFondo.jpg');
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;
            }
        </style>
    </head>
    <body>
        <header class="header">
            <img src="uploads/fotosVitoMaite10/logo.png" class="logo">
            <h1 class="header-title">VitoMaite</h1>
            <a href="index.jsp" class="btn-back">Volver</a>
        </header>
        
        <main class="main-content">
            <div class="register-container">
                <h2>Crear una cuenta:</h2>
                <%-- Si hay mensaje de error se muestra aqui--%>
                <%
                    String mensaje = (String) request.getAttribute("errorMessage");
                    if(mensaje != null){
                %>
                <div class="mensaje">
                    <p><%= mensaje %></p>
                </div>
                <%
                }
                %>
                
                <form action="ServletRegistro" method="post" enctype="multipart/form-data">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
    
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" required>
    
                    <label for="name">Nombre</label>
                    <input type="text" id="name" name="name" required>
    
                    <label for="city">Ciudad</label>
                    <select id="city" name="city" required>
                        <option value="" disabled selected>Selecciona una ciudad</option>
                        <option value="Vitoria">Vitoria</option>
                        <option value="Bilbao">Bilbao</option>
                        <option value="Donosti">Donosti</option>
                    </select>
    
                    <label for="age">Edad</label>
                    <input type="number" id="age" name="age" min="18" max="100" required>
    
                    <label for="latitud">Latitud</label>
                    <input type="number" id="latitud" name="latitud" step="any" required>
    
                    <label for="longitud">Longitud</label>
                    <input type="number" id="longitud" name="longitud" step="any" required>
    
                    <label for="gender">Género</label>
                    <select id="gender" name="gender" required>
                        <option value="" disabled selected>Selecciona tu género</option>
                        <option value="M">M</option>
                        <option value="F">F</option>
                    </select>
    
                    <label for="photo">Foto de Perfil</label>
                    <input type="file" name="photo" id="photo" accept="image/*" required>
    
                    <button type="submit" class="btn-register">Registrarse</button>
    
                    <p class="login-link">¿Ya tienes una cuenta? <a href="login.jsp">Iniciar sesión</a></p>
                </form>

            </div>
        </main>
                
                
        <footer class="footer">
            <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>        
        </footer>
    </body>
</html>
