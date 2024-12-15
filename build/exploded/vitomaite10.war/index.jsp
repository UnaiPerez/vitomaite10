
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VitoMaite10 - Encuentra el amor</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="website icon" type="png" href="uploads/fotosVitoMaite10/logo.png">
        
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
        <%-- Header --%>
        <header class='header'>
            <img src="uploads/fotosVitoMaite10/logo.png" alt="Logo de vitomaite10" class='logo'>
            <h1 class='header-title'>VitoMaite</h1>
            <a href="login.jsp" class="btn-login">Login</a>
        </header>
        
        <%-- Main --%>
        <main class='main-content'>
            <div class="search-container">
                <h2>Buscar Solteros/as</h2>
                <%-- El formulario se envia al servlet --%>
                <form action='ServletBusquedaBasica' method='get'>
                    <div class="form-group">
                        <label for="gender">¿Que estoy buscando?</label>
                        <select id="gender" name="gender" class="input-field" required>
                            <option value="" disable selected>¿Que bsucas?</option>
                            <option value='F'>Bsuco mujer</option>
                            <option value='M'>Busco hombre</option>
                            <option value="todos">Busco ambos</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for='age-min'>Edad minima</label>
                        <input type="number" id='age-min' name='ageMin' class="input-field" min="18" max='100' required>
                    </div>
                    <div class="form-group">
                        <label for='age-max'>Edad maxima</label>
                        <input type='number' class='input-field' id='age-max' name="ageMax" min="18" max="100">
                    </div>
                    <div class="form-group">
                        <label for="city">Ciudad</label>
                        <select id='city' name="city" class='input-field' required>
                            <option value="" disabled selected>Selecciona una ciudad</option>
                            <option value="Vitoria">Vitoria</option>
                            <option value="Bilbao">Bilbao</option>
                            <option value="Donosti">Donosti</option>
                        </select>
                    </div>
                    <button type="submit" class='btn-search'>Buscar</button>
                </form>
            </div>
        </main>
                
        <%-- Footer --%>
        <footer class='footer'>
            <p>&copy; 2024 VitoMaite10 - Encuentra el amor</p>
        </footer>
    </body>
</html>
