package year2015

import kotlin.time.measureTimedValue

private class Box(dimensions: String) {

    private val length: Int
    private val width: Int
    private val height: Int

    init {
        val (l, w, h) = dimensions.split('x').map { it.toInt() }

        length = l
        width = w
        height = h
    }

    fun wrappingPaper(): Int {
        val sideA = length * width
        val sideB = width * height
        val sideC = height * length

        val allSides = listOf(sideA, sideB, sideC)
        val smallestSide = allSides.minOf { it }

        return allSides.sumOf { it * 2 } + smallestSide
    }

    fun ribbonLength(): Int {
        val (x, y) = listOf(length, width, height).sorted().take(2)
        val ribbonPresent = x + x + y + y

        val ribbonBow = length * width * height

        return ribbonPresent + ribbonBow
    }

}

fun main() {
    //tests1()
    //tests2()

    part1()
    part2()
}


private val tests1 = mapOf(
    "2x3x4" to 58,
    "1x1x10" to 43,
)

private val tests2 = mapOf(
    "2x3x4" to 34,
    "1x1x10" to 14,
)


private fun tests1() {

    for((dimensions, expected) in tests1) {
        println("testing $dimensions (should be $expected)")
        val size = Box(dimensions).wrappingPaper()
        println("size: $size")

        require(size == expected) { "wrong result; was expecting $expected, but was $size" }
    }
}

private fun tests2() {

    for((dimensions, expected) in tests2) {
        println("testing $dimensions (should be $expected)")
        val length = Box(dimensions).ribbonLength()
        println("length: $length")

        require(length == expected) { "wrong result; was expecting $expected, but was $length" }
    }
}


private fun part1() {
    val stream = Box::class.java.getResource("/year2015/day2.txt")?.openStream()?.bufferedReader()

    val result = measureTimedValue {
        var result = 0
        stream?.forEachLine { dimensions ->
            val required = Box(dimensions).wrappingPaper()
            result += required
        }
        result
    }

    println("part 1: ${result.value} (in ${result.duration})")
}

private fun part2() {
    val stream = Box::class.java.getResource("/year2015/day2.txt")?.openStream()?.bufferedReader()

    val result = measureTimedValue {
        var result = 0
        stream?.forEachLine { dimensions ->
            val required = Box(dimensions).ribbonLength()
            result += required
        }
        result
    }

    println("part 2: ${result.value} (in ${result.duration})")
}
