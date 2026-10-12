# SmartCraft

Versión actual: `0.1.0-alpha.1`.

Un mod de Minecraft para nuestro grupo de amigos. Bloques con caras, variantes
del grupo y una máquina para elegir cuál quieres: el Smartizador.

## Para jugar

- Minecraft Java Edition **1.20.1**.
- Minecraft Forge **47.4.10**.
- El archivo `.jar` de SmartCraft.

Descarga el `.jar` desde [Releases](https://github.com/AweonasosOfflineDevs/smartcraft/releases)
y colócalo en la carpeta `mods` de tu instalación de Minecraft. Luego inicia
el juego con el perfil de Forge.

Si todavía no hay un `.jar` publicado, puedes descargar una compilación desde
Actions siguiendo las instrucciones de abajo. Para jugar en un servidor,
instala el mod tanto en el servidor como en el cliente y usa la misma versión
en ambos.

## Qué trae

- El bloque Smart y 31 variantes, como Smartgo, Darkgo y SmartSans.
- El Smartizador, con un selector de variantes y una barra de carga.
- Smartgorita bruta, lingotes de Smartgorita y el Smartcore.
- Una pestaña SmartCraft en el inventario creativo.

## Cómo usar el Smartizador

Primero necesitas fabricar estas cosas:

| Objeto | Receta |
| --- | --- |
| Smart | Dos bloques de lana en una fila y dos de tablones justo debajo. |
| Smartcore | Pólvora en el centro y un lingote de Smartgorita arriba, abajo, a la izquierda y a la derecha. |
| Smartizador | Un Smart, un Smartcore y dos bloques de tablones, sin un orden específico. |

La Smartgorita bruta se puede fundir en un horno o un alto horno para obtener
lingotes. La mena todavía no tiene generación natural configurada, así que por
ahora puedes obtener los materiales de Smartgorita desde creativo.

Una vez colocado el Smartizador:

1. Haz clic derecho para abrirlo.
2. Pon bloques Smart en la ranura superior y un Smartcore en la inferior.
3. Elige una variante en el selector. Puedes desplazarte para ver las demás.
4. Retira el resultado. Cada bloque consume un Smart y una carga.

La máquina guarda hasta **20 cargas** y cada Smartcore aporta **80** en total.
Se recarga automáticamente mientras el Smartcore tenga usos disponibles.
Elegir una variante no gasta carga; retirar el bloque sí. El inventario y la
carga se conservan al guardar el mundo.

## Para trabajar en el mod

Necesitas un **JDK 17**. Usa el wrapper de Gradle incluido en el repositorio;
no hace falta instalar Gradle por separado. La primera ejecución descarga las
dependencias y necesita conexión a internet.

En Linux o macOS:

```sh
bash ./gradlew build
```

En Windows, desde PowerShell:

```powershell
.\gradlew.bat build
```

El `.jar` compilado queda en `build/libs/`.

Para abrir Minecraft con el mod en desarrollo, cambia `build` por `runClient`.
Para ejecutar las pruebas de servidor del Smartizador, usa `runGameTestServer`.
Estas pruebas cubren la carga, el guardado y la fabricación con Shift-clic.

Las instrucciones para añadir recetas están en
[Recetas del Smartizador](docs/smartizador-recipes.md).

## Compilar en GitHub y publicar versiones

El flujo [Compilar y publicar SmartCraft](.github/workflows/build.yml) usa
Java 17 y compila en los servidores de GitHub, sin ejecutar Gradle en tu PC.
Se ejecuta al subir cambios a `main`, abrir o actualizar un PR hacia `main`,
o subir una etiqueta que empiece por `v`.

También puedes iniciarlo desde
[Actions](https://github.com/AweonasosOfflineDevs/smartcraft/actions): selecciona
el flujo y pulsa **Run workflow**. Necesitas permisos de escritura para hacerlo.
Cuando una compilación termine correctamente, descarga `smartcraft-jar` desde
la sección **Artifacts** de esa ejecución y extrae el `.jar` del ZIP. Necesitas
iniciar sesión en GitHub para descargarlo. El archivo se conserva durante
14 días; las descargas de Releases no tienen esa caducidad.

Para publicar una versión en el repositorio principal:

1. Ajusta `mod_version` en `gradle.properties` y sube ese cambio a `main`.
2. Crea una etiqueta sobre ese commit, con `v` delante de la versión.
3. Sube la etiqueta al repositorio principal.

Por ejemplo, si `mod_version=0.1.0-alpha.1` y tu remoto `origin` apunta a
`AweonasosOfflineDevs/smartcraft`:

```sh
git switch main
git pull --ff-only origin main
git tag v0.1.0-alpha.1
git push origin v0.1.0-alpha.1
```

Solo ejecutes estos comandos cuando quieras publicar esa versión. Una
compilación correcta de la etiqueta crea la release y adjunta el JAR instalable.
Si la etiqueta no coincide con `mod_version`, el flujo falla antes de compilar.
Las etiquetas subidas a un fork no publican releases. Las ejecuciones manuales
y los PR tampoco las publican. Las versiones con sufijo, como `0.1.0-alpha.1`,
se publican como versiones preliminares.

Este flujo ejecuta `build`, no las pruebas de servidor `runGameTestServer`.
Antes de publicar, comprueba también el mod dentro del juego.

## Créditos y licencia

Smartgo y MultexYT figuran como autores del mod. Los créditos de Forge están
conservados en [CREDITS.txt](CREDITS.txt).

Los metadatos del mod indican `All Rights Reserved`.
[LICENSE.txt](LICENSE.txt) contiene los avisos y la licencia de Forge incluidos
con la plantilla; no define una licencia propia para SmartCraft.

Y sí, Smart: sube las texturas.
