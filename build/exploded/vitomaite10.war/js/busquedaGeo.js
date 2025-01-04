document.addEventListener("DOMContentLoaded", function () {
    const radiusInput = document.getElementById("radius");
    const radiusValue = document.getElementById("radius-value");

    let map, userCircle;

    function initMap(lat, lng) {
        map = new google.maps.Map(document.getElementById("map"), {
            center: { lat, lng },
            zoom: 14,
        });

        userCircle = new google.maps.Circle({
            strokeColor: "#FF0000",
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: "#FF0000",
            fillOpacity: 0.35,
            map,
            center: { lat, lng },
            radius: parseFloat(radiusInput.value) * 1000,
        });
    }

    function updateMap(radius) {
        if (userCircle) {
            userCircle.setRadius(radius * 1000);
        }
    }

    function fetchAndDisplayResults(lat, lng, radius) {
        fetch(`ServletBusquedaGeolocalizacion?latitud=${lat}&longitud=${lng}&radio=${radius}`)
            .then((response) => response.json())
            .then((usuarios) => {
                usuarios.forEach((usuario) => {
                    const marker = new google.maps.Marker({
                        position: { lat: usuario.latitud, lng: usuario.longitud },
                        map: map,
                        title: `${usuario.nombre}, ${usuario.edad} años`,
                    });

                    const infoWindow = new google.maps.InfoWindow({
                        content: `<strong>${usuario.nombre}</strong><br>Edad: ${usuario.edad}`,
                    });

                    marker.addListener("click", () => {
                        infoWindow.open(map, marker);
                    });
                });
            })
            .catch((error) => console.error("Error al obtener resultados:", error));
    }

    function getUserLocationAndInitMap() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const lat = position.coords.latitude;
                    const lng = position.coords.longitude;

                    initMap(lat, lng);
                    fetchAndDisplayResults(lat, lng, parseFloat(radiusInput.value));
                },
                (error) => {
                    console.error("Error al obtener ubicación:", error);
                    alert("No se pudo obtener tu ubicación actual.");
                }
            );
        } else {
            alert("Tu navegador no soporta la geolocalización.");
        }
    }

    radiusInput.addEventListener("input", function () {
        const radius = parseFloat(radiusInput.value);
        radiusValue.textContent = `${radius} km`;

        if (map) {
            updateMap(radius);
        }
    });

    getUserLocationAndInitMap();
});


