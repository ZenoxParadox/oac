package year2015

import util.isOdd
import util.readFile
import kotlin.time.measureTimedValue

fun main() {
    //tests1()
    tests2()

    val input = readFile("/year2015/day3.txt")

    //part1(input.readText().trim())
    part2(input.readText().trim())
}

private data class House(val x: Int, val y: Int)
private data class Santa(var x: Int, var y: Int)

private class HouseCounter {

    private val visited = mutableMapOf<House, Int>()

    private val santa = Santa(0, 0)
    private val roboSanta = Santa(0, 0)

    init {
        visited[House(0, 0)] = 2 // default house
    }

    fun houseCounter(input: String): Int {
        println("houseCounter: $input")

        input.forEachIndexed { index, direction ->

            val santaMoves = index.isOdd

            when (direction) {
                '>' -> {
                    if (santaMoves) {
                        santa.x++
                    } else {
                        roboSanta.x++
                    }
                }

                '<' -> {
                    if (santaMoves) {
                        santa.x--
                    } else {
                        roboSanta.x--
                    }
                }

                '^' -> {
                    if (santaMoves) {
                        santa.y++
                    } else {
                        roboSanta.y++
                    }
                }

                'v' -> {
                    if (santaMoves) {
                        santa.y--
                    } else {
                        roboSanta.y--
                    }
                }

                else -> {
                    throw IllegalArgumentException("Unknown direction [$direction]")
                }
            }

            //
            val house = if (santaMoves) {
                House(santa.x, santa.y)
            } else {
                House(roboSanta.x, roboSanta.y)
            }

            if (visited.containsKey(house)) {
                visited[house] = visited.getValue(house) + 1
            } else {
                visited[house] = 1
            }
        }

        return visited.size
    }


}

private val tests1 = mapOf(
    ">" to 2,
    "^>v<" to 4,
    "^v^v^v^v^v" to 2,
)

private val tests2 = mapOf(
    "^v" to 3,
    "^>v<" to 3,
    "^v^v^v^v^v" to 11,
)

private fun tests1() {

    for ((directions, expected) in tests1) {
        println("testing $directions (should be $expected)")

        val numberOfHouses = HouseCounter().houseCounter(directions)
        println("numberOfHouses $numberOfHouses")

        require(numberOfHouses == expected) { "wrong result; was expecting $expected, but was $numberOfHouses" }
    }
}

private fun tests2() {

    for ((directions, expected) in tests2) {
        println("testing $directions (should be $expected)")

        val numberOfHouses = HouseCounter().houseCounter(directions)
        println("numberOfHouses $numberOfHouses")

        require(numberOfHouses == expected) { "wrong result; was expecting $expected, but was $numberOfHouses" }
    }
}

private fun part1(directions: String) {
    println("directions $directions")

    val numberOfHouses = HouseCounter().houseCounter(directions)
    println("numberOfHouses $numberOfHouses")
}

private fun part2(directions: String) {
    println("directions $directions")

    val result = measureTimedValue {
        HouseCounter().houseCounter(directions)
    }

    println("part 2: ${result.value} (took ${result.duration})")
}
