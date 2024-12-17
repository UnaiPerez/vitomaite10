
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultados de la Búsqueda</title>
    <link rel="website icon" href="uploads/fotosVitoMaite10/logo.png" type='png'>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/resultadosLogueado.css">
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
        <img src="uploads/fotosVitoMaite10/logo.png" alt="Logo de VitoMaite" class="logo">
        <h1 class="header-title">Resultados de la Búsqueda</h1>
        <a href="pantallaLogueado.jsp" class="btn-back">Volver</a>
    </header>

    <main class='main-content'>
        <div id="results-container">
            <%
                ArrayList<String> resultados = (ArrayList<String>) request.getAttribute("resultados");
                if(resultados != null && !resultados.isEmpty()){
                    for (String resultado : resultados){
                        out.println(resultado);
                    }
                } else {
                    out.println("<p>No se encontraron resultados.</p>");
                }
            %>
        </div>
    </main>

    <footer class="footer">
        <p>&copy; 2024 VitoMaite10 - Encuentra el amor</p>
    </footer>
</body>
</html>

