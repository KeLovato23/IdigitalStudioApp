# Guía de Desarrollo de la App

Este proyecto fue desarrollado utilizando **Android Studio Jellyfish 2023.3.1** y **Java 22.0.1** (2024-04-16). La aplicación está configurada con un **minSdk** de 24 y un **targetSdk** de 34, lo que asegura compatibilidad con una amplia gama de dispositivos y la posibilidad de aprovechar las últimas características de Android.

## Configuraciones Clave

- **minSdk 24**: La aplicación requiere un mínimo de Android 7.0 (Nougat) para ejecutarse, lo que garantiza compatibilidad con dispositivos desde esa versión en adelante.
- **targetSdk 34**: Se ha configurado la aplicación para apuntar a Android 14, lo que permite aprovechar las características más recientes del sistema operativo Android.
- **Android Studio Jellyfish 2023.3.1**: Se utilizó esta versión de Android Studio para el desarrollo del proyecto, asegurando el uso de las últimas herramientas de desarrollo de Android.
- **Java 22.0.1 (2024-04-16)**: La versión de Java utilizada es la **22.0.1**, asegurando compatibilidad con las librerías y las funcionalidades del sistema.


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

## Dependencias Utilizadas en el Proyecto

En este proyecto se han utilizado varias dependencias para garantizar un funcionamiento óptimo y eficiente de la aplicación. A continuación, se describen las dependencias más relevantes y para qué se utilizan:

### Implementaciones Clave

```gradle
implementation libs.androidx.core.ktx
implementation libs.androidx.appcompat
implementation libs.material
implementation libs.androidx.activity
implementation libs.androidx.constraintlayout
implementation libs.retrofit
implementation libs.retrofit.gson
implementation libs.androidx.lifecycle.viewmodel.ktx
implementation libs.androidx.lifecycle.livedata.ktx
implementation libs.recyclerview
implementation libs.okhttp.logging
implementation libs.glide
implementation libs.androidx.junit.ktx
implementation libs.androidx.lifecycle.service
implementation libs.androidx.navigation.fragment.ktx
implementation libs.androidx.navigation.ui.ktx
kapt libs.glide.compiler

// Room usando una variable para la versión
def room_version = "2.5.1"
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"
implementation "androidx.room:room-ktx:$room_version"

testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'


