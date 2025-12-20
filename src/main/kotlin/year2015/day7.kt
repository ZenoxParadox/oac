@file:Suppress("SpellCheckingInspection")

package year2015

import util.readFile
import java.io.BufferedReader
import kotlin.collections.set
import kotlin.experimental.inv
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

private val tests1 = listOf(
    "123 -> x",
    "456 -> y",
    "x AND y -> d",
    "x OR y -> e",
    "x LSHIFT 2 -> f",
    "y RSHIFT 2 -> g",
    "NOT x -> h",
    "NOT y -> i"
)

/*
d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456

 */

fun main() {
    //tests1()

    val input = readFile("/year2015/day7.txt")
    part1(input)
}

private class GateKeeper {

    private val results = mutableMapOf<String, UShort>()
    private val rawInstructions = mutableMapOf<String, String>()

    fun setInstructions(instructionList: List<String>) {
        for (i in instructionList) {
            val (info, target) = i.split(" -> ")
            rawInstructions[target] = info
        }
    }

    fun getResult(): Map<String, UShort>{
        return results.toSortedMap()
    }

    fun getResult(target: String): UShort? =
        results[target]

    fun resetAll() {
        results.clear()
    }

    fun overrideSignal(target: String, value: UShort) {
        rawInstructions[target] = value.toString()
    }

    fun assemble() {
        println("Starting assemply; instructions: ${rawInstructions.size}")
        val instructions = rawInstructions.toMutableMap()
        var cycles = 0

        while (instructions.isNotEmpty()) {
            val iterator = instructions.iterator()

            iterator.forEach { (target, info) ->
                when {
                    info.contains("AND") -> {
                        val (source1, _, source2) = info.split(" ")

                        val source1AsUshort = source1.toUShortOrNull()

                        if(source1AsUshort == null){
                            if (results.containsKey(source1) && results.containsKey(source2)) {
                                results[target] = results[source1]!!.and(results[source2]!!)
                                iterator.remove()
                            }
                        } else {
                            // source1 is a value
                            if (results.containsKey(source2)) {
                                results[target] = source1AsUshort.and(results[source2]!!)
                                iterator.remove()
                            }
                        }
                    }

                    info.contains("OR") -> {
                        val (source1, _, source2) = info.split(" ")

                        if (results.containsKey(source1) && results.containsKey(source2)) {
                            results[target] = results[source1]!!.or(results[source2]!!)
                            iterator.remove()
                        }
                    }

                    info.contains("LSHIFT") -> {
                        val (source, _, value) = info.split(" ")

                        if (results.containsKey(source)) {
                            results[target] = results[source]!!.shl(value.toUShort())
                            iterator.remove()
                        }
                    }

                    info.contains("RSHIFT") -> {
                        val (source, _, value) = info.split(" ")

                        if (results.containsKey(source)) {
                            results[target] = results[source]!!.shr(value.toUShort())
                            iterator.remove()
                        }
                    }

                    info.contains("NOT") -> {
                        val (_, source) = info.split(" ")

                        if (results.containsKey(source)) {
                            results[target] = results[source]!!.inv()
                            iterator.remove()
                        }
                    }

                    else -> {
                        val value = info.toUShortOrNull()
                        if (value != null) {
                            results[target] = value // it's a value
                            iterator.remove()
                        } else {
                            // must be a reference: z -> lx (info -> target)
                            if (results.contains(info)) {
                                results[target] = results[info]!!
                                iterator.remove()
                            }
                        }
                    }
                }
            }

            cycles++
        }

        println("Done; used $cycles cycles.")
    }

}

private fun UShort.shr(other: UShort): UShort =
    this.toInt().shr(other.toInt()).toUShort()

private fun UShort.shl(other: UShort): UShort =
    this.toInt().shl(other.toInt()).toUShort()

private fun tests1() {
    println("tests1")

    val gateKeeper = GateKeeper()
    gateKeeper.setInstructions(tests1)
    gateKeeper.assemble()
    for((letter, value) in gateKeeper.getResult()){
        println("$letter = $value")
    }
}

private fun part1(reader: BufferedReader) {
    val gateKeeper = GateKeeper()
    val duration1 = measureTime {
        gateKeeper.setInstructions(reader.readLines())
        gateKeeper.assemble()
    }

    val signalA = gateKeeper.getResult("a")
    println("signalA: $signalA (took: $duration1)")

    // Part 2
    val duration2 = measureTime {
        gateKeeper.resetAll()
        gateKeeper.overrideSignal("b", signalA!!)
        gateKeeper.assemble()
    }

    val signalA2 = gateKeeper.getResult("a")
    println("signalA2: $signalA2 (took: ${(duration1 + duration2)})")
}
