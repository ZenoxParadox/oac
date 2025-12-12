@file:Suppress("SpellCheckingInspection")

package year2015

import util.readFile
import java.io.BufferedReader
import kotlin.time.measureTime

private val tests1 = listOf(
    "turn on 0,0 through 999,999", //  would turn on (or leave on) every light.
    "toggle 0,0 through 999,0", // would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
    "turn off 499,499 through 500,500" //  would turn off (or leave off) the middle four lights.
)

fun main() {
    // tests1()

    val input = readFile("/year2015/day6.txt")

    //part1(input)
    part2(input)
}

private class LightManager() {

    private val lights = Array(1000) {
        Array(1000) {
            false
        }
    }

    private val instructionMatcher =
        Regex("(?<mode>turn on|turn off|toggle) (?<fromx>[0-9]{1,3}),(?<fromy>[0-9]{1,3}) through (?<tox>[0-9]{1,3}),(?<toy>[0-9]{1,3})")

    fun followInstruction(instruction: String) {
        println("instruction: $instruction")
        val result = instructionMatcher.find(instruction)!!

        val mode = result.groups["mode"]?.value
        println("mode: $mode")

        val fromX = result.groups["fromx"]?.value?.toInt()!!
        val fromY = result.groups["fromy"]?.value?.toInt()!!
        val toX = result.groups["tox"]?.value?.toInt()!!
        val toY = result.groups["toy"]?.value?.toInt()!!

        println("$fromX:$fromY -> $toX:$toY")

        for (y in fromY..toY) {
            for (x in fromX..toX) {
                when (mode) {
                    "toggle" -> lights[y][x] = !lights[y][x]
                    "turn on" -> lights[y][x] = true
                    "turn off" -> lights[y][x] = false
                }
            }
        }
    }

    fun getLightCount(): Int {
        var count = 0

        for (y in lights.indices) {
            for (x in lights[y].indices) {
                if (lights[y][x]) {
                    count++
                }
            }
        }

        return count
    }

}

//

private class LightManager2() {

    private val lights = Array(1000) {
        Array(1000) {
            0
        }
    }

    private val instructionMatcher =
        Regex("(?<mode>turn on|turn off|toggle) (?<fromx>[0-9]{1,3}),(?<fromy>[0-9]{1,3}) through (?<tox>[0-9]{1,3}),(?<toy>[0-9]{1,3})")

    fun followInstruction(instruction: String) {
        println("instruction: $instruction")
        val result = instructionMatcher.find(instruction)!!

        val mode = result.groups["mode"]?.value
        println("mode: $mode")

        val fromX = result.groups["fromx"]?.value?.toInt()!!
        val fromY = result.groups["fromy"]?.value?.toInt()!!
        val toX = result.groups["tox"]?.value?.toInt()!!
        val toY = result.groups["toy"]?.value?.toInt()!!

        println("$fromX:$fromY -> $toX:$toY")

        for (y in fromY..toY) {
            for (x in fromX..toX) {
                when (mode) {
                    "turn on" -> lights[y][x]++
                    "turn off" -> {
                        lights[y][x] = (lights[y][x] - 1).coerceAtLeast(0)
                    }
                    "toggle" -> lights[y][x] = lights[y][x] + 2
                }
            }
        }
    }

    fun getLightCount(): Int {
        var count = 0

        for (y in lights.indices) {
            for (x in lights[y].indices) {
                count += lights[y][x]
            }
        }

        return count
    }

}


private fun tests1() {

    //val regex = Regex("(turn on|toggle|turn off) (0-9,0-9) through (0-9,0-9)")

    val manager = LightManager()

    for (instruction in tests1) {
        manager.followInstruction(instruction)
    }

    val count = manager.getLightCount()
    println("count: $count")

//    for ((sequence, expected) in tests1) {
//        logger.debug { "testing $sequence (should be $expected) -----------------------------" }
//
//        //require(isNice == expected) { "wrong result; was expecting $expected, but was $isNice" }
//    }
}

private fun part1(reader: BufferedReader) {
    val manager = LightManager()

    val duration = measureTime {
        reader.forEachLine {
            manager.followInstruction(it)
        }
    }

    val count = manager.getLightCount()
    println("(part 1) count: $count (took: $duration)")
}

private fun part2(reader: BufferedReader) {
    val manager = LightManager2()

    val duration = measureTime {
        reader.forEachLine {
            manager.followInstruction(it)
        }
    }

    // > 12.884.956
    //   17.836.115 = correct
    val count = manager.getLightCount()
    println("(part 2) count: $count (took: $duration)")
}
