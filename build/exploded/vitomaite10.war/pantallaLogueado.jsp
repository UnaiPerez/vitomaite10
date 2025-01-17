
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pantalla Logueado - VioMaiet10</title>
        <link rel="stylesheet" href='css/pantallaLogueado.css'>
        <link rel="website icon" type="png" href='uploads/fotosVitoMaite10/logo.png'>
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
            <img class='logo' src='uploads/fotosVitoMaite10/logo.png' alt='Logo de VitoMaite'>
            <h1 class='header-title'>VitoMaite</h1>
            <div class='userInfo'>
                <%
                    String nombre = (String) session.getAttribute("nombre");
                    String foto = (String) session.getAttribute("foto");
                    if(nombre != null){
                %>
                    <span id='username'>¡Hola, <%= nombre %>!</span>
                    <% if (!foto.startsWith("uploads/")) { %>
                        <img src="data:image/jpeg;base64,<%= foto %>" alt="Foto de perfil" class="userPhoto">
                    <% } else { %>
                        <img src="<%= foto %>" alt="Foto de perfil" class="userPhoto">
                    <% } %>
                    <form action="ServletLogout" method='post'>
                        <button type="submit" class='btn-logout'>Logout</button>
                    </form>
                    <% } else { 
                       
                    %>
                       response.sendRedirect("login.jsp");
                       return;
                    <% } %>
            </div>
        </header>
            
            
     <%-- Funcionalidades --%>
     <div class='functionalities'>
         <a href='CargarPerfil'>Modificar Perfil</a>
         <div class='dropdown'>
             <span class='dropdown-label'>Aficiones</span>
             <div class='dropdown-menu'>
                 <a href="ServletCargarAficiones">Añadir aficion</a>
                 <a href="ServletCargarAficionesUsuario">Eliminar aficion</a>
                 <a href="ServletVerAficiones">Ver aficiones</a>
             </div>
         </div>
         <div class="dropdown">
             <span class="dropdown-label">Busqueda avanzada</span>
             <div class='dropdown-menu'>
                 <a href='ServletTodasAficiones'>Aficiones</a>
                 <a href='BusquedaGeo.jsp'>Geolocalizacion</a>
             </div>
         </div>
         <a href="ServletVerLikes">Ver likes</a>
         <a href="ServletMatching">Buscar matching</a>
     </div>
     
     <%-- Main --%>
     <main class='main-content'>
         <div class='search-container'>
             <h2>Buscar solteros/as</h2>
             <form action='ServletBusquedaLogueado' method='get'>
                 <div class='form-group'>
                     <label for='gender'>¿Que estoy buscando?</label>
                     <select id='gender' name="gender" class="input-field">
                         <option value="" disabled selected>¿Que buscas?</option>
                         <option value='F'>Busco mujer</option>
                         <option value='M'>Busco hombre</option>
                         <option value="todos">Busco ambos</option>
                     </select>
                 </div>
                 <div class="form-group">
                     <label for='age-min'>Edad minima</label>
                     <input type="number" id='age-min' name='ageMin' class='input-field' min="18" max='100'>
                 </div>
                 <div class="form-group">
                     <label for='age-max'>Edad maxima</label>
                     <input type='number' id='age-max' name='ageMax' class='input-field' min="18" max='100'>
                 </div>
                 <div class="form-group">
                     <label for="ciudad">Ciudad</label>
                     <select id="city" name='city' class='input-field'>
                         <option value="" disabled selected>Selecciona una ciudad</option>
                         <option value='Vitoria'>Vitoria</option>
                         <option value='Bilbao'>Bilbao</option>
                         <option value='Donosti'>Donosti</option>
                     </select>
                 </div>
                 <button type="submit" class='btn-search-logueado'>Buscar</button>
             </form>
         </div>
     </main>
     
     <%-- Footer --%>
     <footer class='footer'>
         <p>&copy;2024 VitoMaite10 - Encuentra el amor</p>
     </footer>
    </body>
</html>
