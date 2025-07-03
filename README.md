```markdown
# SpeechAndText

Una aplicación Android de demostración construida con Jetpack Compose que permite:

- 🎙️ **Reconocimiento de voz** (Speech-to-Text) en tiempo real.  
- 🔊 **Síntesis de voz** (Text-to-Speech) para leer texto en voz alta.  
- 🌈 **Theming personalizado** con paleta clara/oscura y degradados.  
- 📱 **Navegación** mediante una barra inferior con iconos propios y highlight circular.  
- 🛡️ **Estado de “Kuntur”** on/off con iconos `ic_kuntur_on` / `ic_kuntur_off`.  

---

## 📂 Estructura del proyecto

```
app/
├── src/main/
│   ├── java/com/denicks21/speechandtext/
│   │   ├── MainActivity.kt
│   │   ├── navigation/
│   │   │   ├── NavGraph.kt
│   │   │   └── NavScreens.kt
│   │   ├── screen/
│   │   │   ├── HomePage.kt
│   │   │   ├── SpeechToTextPage.kt
│   │   │   └── TextToSpeechPage.kt
│   │   └── ui/
│   │       ├── composables/
│   │       │   └── AppBottomBar.kt
│   │       └── theme/
│   │           ├── Color.kt
│   │           └── Theme.kt
│   └── res/
│       ├── drawable/       ← íconos y vectores (incluye ic_ubication, ic_kuntur_on/off…)
│       └── values/         ← colors.xml, themes.xml…
└── build.gradle
```

---

## 🚀 Tecnologías

- Kotlin  
- Jetpack Compose  
- Navigation Compose  
- Material Theme con Light/Dark  
- Recursos vectoriales en `res/drawable`

---

## 📥 Instalación

1. Clona este repositorio:  
   ```bash
   git clone https://github.com/tu-usuario/SpeechAndText.git
   ```
2. Abre en Android Studio.<br>
3. Sync Gradle y ejecuta en un emulador o dispositivo.

---

## 🛠️ Uso básico

- En la pantalla principal:
  1. Pulsa **Activar Kuntur** para iniciar reconocimiento de voz.  
  2. Verás el icono y texto de estado cambiar a “a la escucha”.  
  3. Habla para ver tu texto transcrito en tiempo real.  
- Usa la barra inferior para navegar entre **Monitor**, **Historial**, **Contactos** y **Mapa**.

---

## ♻️ Contribuir

1. Haz **fork** del proyecto.  
2. Crea una rama: `git checkout -b feature/nueva-funcionalidad`.  
3. Realiza tus cambios y **commit**.  
4. Abre un **Pull Request** describiendo tu aporte.

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**.  
```
