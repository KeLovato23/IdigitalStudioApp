# Guía de Desarrollo de la App

A continuación se presentan las distintas pantallas y funcionalidades de la aplicación, acompañadas de capturas de pantalla que ilustran cada paso.

## 1. Icono de la aplicación
Esta es la imagen del **icono de la aplicación**, el cual será mostrado en el dispositivo móvil al momento de instalar la app.

<img src="https://github.com/user-attachments/assets/4d603588-1215-4bce-be11-51a994a64d9e" alt="Icono de la app" width="300"/>

---

## 2. Pantalla de Splash (Splash Screen)
La segunda imagen corresponde a la **pantalla de Splash** que se muestra cuando se inicia la aplicación. Esta pantalla ofrece una breve vista mientras se cargan los recursos iniciales.

<img src="https://github.com/user-attachments/assets/5d1908dd-ccd6-4159-a830-a447fb903a63" alt="Splash Screen" width="300"/>

---

## 3. Lista de Pokémon
En esta pantalla se muestra una **lista de Pokémon**. Aquí se implementó una funcionalidad para almacenar los datos localmente en caso de que no haya conexión a Internet. Además, si el usuario sigue navegando hacia abajo, se mostrará una imagen de carga que indicará que se están cargando más Pokémon. No se vuelven a hacer consultas a la API de Pokémon una vez que los datos se cargan inicialmente, optimizando así los recursos.

<img src="https://github.com/user-attachments/assets/8e2777fe-3472-43e3-bad2-4ec9a289872c" alt="Lista de Pokémon" width="300"/>

---

## 4. Detalle del Pokémon
Esta pantalla muestra el **detalle de un Pokémon**, con un diseño modificado para exhibir las estadísticas del Pokémon. Además, incluye un botón que permite al usuario agregar al Pokémon como favorito. También hay un botón de navegación que facilita regresar a la pantalla anterior para mejorar la accesibilidad.

<img src="https://github.com/user-attachments/assets/e4895155-abb2-49ae-88e1-9b5ca57b27d3" alt="Detalle del Pokémon" width="300"/>

---

## 5. Confirmación de Favorito
Cuando el usuario presiona el botón de **"Agregar a favoritos"**, se muestra un mensaje indicando que el Pokémon ha sido agregado a la lista de favoritos. Cuando el usuario vuelva a la pantalla de detalle de dicho Pokémon, el icono de favorito ya aparecerá como seleccionado.

<img src="https://github.com/user-attachments/assets/2456c05c-af4b-42cc-a3d7-ae249de47cb8" alt="Confirmación de Favorito" width="300"/>

---

## 6. Compartir Pokémon
Esta pantalla muestra la funcionalidad de **compartir un Pokémon**. En este caso, el usuario tiene la opción de seleccionar una aplicación para compartir el Pokémon, como **WhatsApp**, como se observa en la imagen. Esto facilita al usuario compartir los detalles del Pokémon directamente desde la app.

<img src="https://github.com/user-attachments/assets/6056af9d-8d2b-417c-89a0-b662b58024bf" alt="Compartir Pokémon en WhatsApp" width="300"/>




## 7. Compartir Pokémon en WhatsApp
En esta pantalla se muestra cómo el usuario puede **compartir los datos de un Pokémon** a través de WhatsApp. El usuario selecciona la aplicación y puede enviar información detallada del Pokémon a sus contactos de WhatsApp de manera rápida y sencilla.

<img src="https://github.com/user-attachments/assets/d10e2aed-cc99-41f7-8b37-051b71723d9f" alt="Compartiendo Pokémon en WhatsApp" width="300"/>

---

## 8. Pokémon Agregados como Favoritos
Esta pantalla muestra la **lista de Pokémon que el usuario ha marcado como favoritos**. Cada vez que el usuario añade un Pokémon a sus favoritos, este aparece en esta sección, permitiendo un fácil acceso a sus Pokémon favoritos en cualquier momento.

<img src="https://github.com/user-attachments/assets/b18b5017-c400-4f08-a23c-2f6a449ab4b8" alt="Lista de Pokémon Favoritos" width="300"/>



