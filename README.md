# Proyecto III: Mundo Cubo - Ciudad Perdida de la Atlántida

Este proyecto consiste en el desarrollo de un algoritmo para calcular el volumen de agua que puede almacenar una ciudad construida en el videojuego **Mundo Cubo**. La ciudad se modela como una matriz de $n \times m$ celdas, donde cada celda contiene una torre de cubos de una altura determinada.

## Integrantes
**Nombre:** Bueno, Santiago - **Carnet:** 20-10168
**Nombre:** Toufic, Zahir - **Carnet:** 21-10600  

---

## Descripción del Problema
El objetivo es determinar cuántos cubos de agua puede contener la ciudad antes de que el líquido se desborde por los bordes. Se asume que fuera de los límites de la ciudad la altura es 0, por lo que cualquier agua que alcance el borde y no tenga una pared más alta se filtrará.


## Implementación

La solución implementada en `AlfonsoJose.kt` utiliza un enfoque basado en el **Algoritmo de Dijkstra** (o una búsqueda de costo uniforme) para procesar las celdas desde los bordes hacia el interior.

### Detalles Técnicos:
1.  **Cola de Prioridad (Min-Heap):** Se utiliza una `PriorityQueue` para almacenar las celdas del borde de la ciudad. La cola prioriza las celdas con la menor altura, ya que el agua siempre se desbordará por el punto más bajo del "recipiente" que la contiene.
2.  **Procesamiento de Celdas:** * Se marcan todas las celdas del borde como visitadas y se añaden a la cola.
    * Mientras la cola no esté vacía, se extrae la celda con la altura mínima actual ($h$).
    * Se exploran sus vecinos adyacentes. Si un vecino tiene una altura menor que $h$, la diferencia ($h - \text{altura\_vecino}$) representa el agua atrapada en esa celda.
    * La "altura efectiva" del vecino se actualiza al máximo entre su altura original y la altura de la celda desde la que se accedió, para asegurar que el nivel del agua se mantenga consistente.
3.  **Complejidad:** La complejidad temporal es $O(N \cdot M \log(N \cdot M))$, donde $N \cdot M$ es el número total de celdas en la matriz.

### Flexibilidad de Formato
El programa es capaz de detectar dos tipos de entrada en `atlantis.txt`:
* **Con dimensiones:** La primera línea contiene $n$ y $m$, seguida de los datos de la matriz.
* **Solo matriz:** El archivo contiene directamente los valores de las torres, infiriendo las dimensiones por el número de líneas y columnas detectadas.

---

## Instrucciones de Uso

### Requisitos
* Kotlin Compiler (kotlinc)
* Java Runtime Environment (JRE)

### Compilación y Ejecución
Para compilar y ejecutar el proyecto, utiliza los siguientes comandos en tu terminal:

```bash
# Compilación
kotlinc AlfonsoJose.kt -include-runtime -d AlfonsoJose.jar

# Ejecución (asegúrate de tener el archivo atlantis.txt en la misma carpeta)
java -jar AlfonsoJose.jar
```

### Ejemplo de Entrada (`atlantis.txt`)
```text
3 3 4 4 4 2
3 1 3 2 1 4
7 3 1 6 4 1
```

**Salida esperada:** `5` 

---

## Estructura del Repositorio
* `AlfonsoJose.kt`: Contiene la lógica del algoritmo y el método `main` para la lectura de archivos.
* `README.md`: Identificación de los integrantes y explicación de la implementación.
* `atlantis.txt`: Archivo de entrada con la configuración de la ciudad (no incluido por defecto).
