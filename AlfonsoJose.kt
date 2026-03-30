import java.io.File
import java.util.*

class AlfonsoJose {
    data class Cell(val x: Int, val y: Int, val height: Int)

    fun computeWater(heights: Array<IntArray>): Int {
        val n = heights.size
        val m = heights[0].size
        if (n < 3 || m < 3) return 0 // No hay celdas interiores que puedan retener agua

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
                    val newHeight = maxOf(h, heights[nx][ny])
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
    val tokens = file.readText().trim().split(Regex("\\s+")).map { it.toInt() }
    if (tokens.size < 2) {
        println("Archivo vacío o mal formado")
        return
    }
    val n = tokens[0]
    val m = tokens[1]
    if (tokens.size < 2 + n * m) {
        println("El archivo no contiene suficientes datos para la matriz")
        return
    }

    val heights = Array(n) { i ->
        IntArray(m) { j ->
            tokens[2 + i * m + j]
        }
    }

    val result = AlfonsoJose().computeWater(heights)
    println(result)
}
