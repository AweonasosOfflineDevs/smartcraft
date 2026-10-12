# Recetas del Smartizador

La entrada superior acepta el bloque smartcraft:smart. La inferior acepta un
Smartcore. La barra almacena 20 cargas y cada resultado consume una cabeza y una
carga al retirarlo. El Smartcore repone automaticamente lo gastado, hasta agotar
sus 80 usos. El inventario y la carga se guardan con el bloque.

Para agregar una variante, crea un JSON en
src/main/resources/data/smartcraft/recipes/ con este formato:

```json
{
  "type": "smartcraft:smartizador",
  "ingredient": {"item": "smartcraft:smart"},
  "result": "smartcraft:darkgo"
}
```

El ejemplo describe el formato. Las 31 variantes de cabeza estan registradas en recipes/smartizador; el bloque Smart base, la mesa y la mena no son resultados del selector. Cada receta produce
un bloque y aparece en el selector solo cuando su ingrediente esta presente.
El orden del selector corresponde al identificador de las recetas.

La pantalla usa smartizador_gui.png y smartizador_barra.png (14 x 56). La barra se recorta de abajo hacia arriba segun la carga almacenada.

Pruebas de servidor:
```powershell
.\gradlew.bat runGameTestServer --offline
```
