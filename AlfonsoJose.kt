import java.io.File
import java.util.*

class AlfonsoJose {
    data class Cell(val x: Int, val y: Int, val height: Int)

    /**
     * Calcula el volumen de agua que puede almacenar una ciudad representada por una matriz de alturas.
     * Utiliza el algoritmo de Dijkstra desde los bordes (similar a A* con heurística 0).
     *
     * @param heights Matriz de alturas (n x m)
     * @return Número de cubos de agua
     */
    fun computeWater(heights: Array<IntArray>): Int {
        val n = heights.size
        val m = heights[0].size
        if (n < 3 || m < 3) return 0 // No hay celdas interiores

        val visited = Array(n) { BooleanArray(m) }
        val pq = PriorityQueue<Cell>(compareBy { it.height })

        // Agregar todas las celdas del borde a la cola de prioridad
        for (i in 0 until n) {
            pq.add(Cell(i, 0, heights[i][0]))
            pq.add(Cell(i, m - 1, heights[i][m - 1]))
            visited[i][0] = true
            visited[i][m - 1] = true
        }
        for (j in 1 until m - 1) {
            pq.add(Cell(0, j, heights[0][j]))
            pq.add(Cell(n - 1, j, heights[n - 1][j]))
            visited[0][j] = true
            visited[n - 1][j] = true
        }

        val dirs = arrayOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
        var water = 0

        while (pq.isNotEmpty()) {
            val (x, y, h) = pq.poll()
            for ((dx, dy) in dirs) {
                val nx = x + dx
                val ny = y + dy
                if (nx in 0 until n && ny in 0 until m && !visited[nx][ny]) {
                    visited[nx][ny] = true
                    // La nueva altura efectiva es la máxima entre la actual y la del vecino
                    val newHeight = maxOf(h, heights[nx][ny])
                    // El agua atrapada en esta celda es la diferencia positiva
                    water += maxOf(0, h - heights[nx][ny])
                    pq.add(Cell(nx, ny, newHeight))
                }
            }
        }
        return water
    }
}

fun main() {
    val file = File("atlantis.txt")
    val lines = file.readLines().filter { it.isNotBlank() }

    // Detectar formato: si la primera línea contiene exactamente dos números y el total de números
    // en el archivo es igual a n*m + 2, se interpreta como formato con dimensiones.
    // En caso contrario, se asume que el archivo contiene únicamente la matriz.
    val allTokens = lines.flatMap { it.trim().split(Regex("\\s+")) }.map { it.toInt() }

    val n: Int
    val m: Int
    val heights: Array<IntArray>

    if (allTokens.size >= 2 && lines[0].trim().split(Regex("\\s+")).size == 2) {
        // Formato: primera línea con "n m", luego n líneas con m números cada una
        n = allTokens[0]
        m = allTokens[1]
        val expectedSize = 2 + n * m
        if (allTokens.size < expectedSize) {
            println("Error: el archivo no contiene suficientes números.")
            return
        }
        val heightTokens = allTokens.drop(2)
        heights = Array(n) { i ->
            IntArray(m) { j ->
                heightTokens[i * m + j]
            }
        }
    } else {
        // Formato: solo la matriz, se infieren n y m del número de líneas y tokens por línea
        val rows = lines.size
        if (rows == 0) {
            println("Error: archivo vacío.")
            return
        }
        val cols = lines[0].trim().split(Regex("\\s+")).size
        // Verificar que todas las líneas tengan la misma cantidad de números
        for (line in lines) {
            if (line.trim().split(Regex("\\s+")).size != cols) {
                println("Error: las filas tienen diferente número de columnas.")
                return
            }
        }
        n = rows
        m = cols
        heights = Array(n) { i ->
            val tokens = lines[i].trim().split(Regex("\\s+")).map { it.toInt() }
            tokens.toIntArray()
        }
    }

    val result = AlfonsoJose().computeWater(heights)
    println(result)
}
