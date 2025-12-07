package year2015

import java.security.MessageDigest
import kotlin.time.measureTimedValue

fun main() {
    //tests1()
    //tests2()

    part1() // 361.428250ms
    part2() // 7.632691041s
}

private class Hasher(private val salt: String, private val needle: String) {

    private val md = MessageDigest.getInstance("MD5")

    fun getFirstHashWithLeadingZeros(): Int {
        var i = 0
        while (true) {
            val hash = toMd5(salt + i)

            if (hash.startsWith(needle)) {
                return i
            }

            i++
        }
    }

    private fun toMd5(input: String): String {
        val bytes = md.digest(input.toByteArray())
        return bytes.joinToString("") { String.format("%02x", it) }
    }

}


private val tests1 = mapOf(
    "abcdef" to 609043, // abcdef609043 -> 000001dbbfa...
    "pqrstuv" to 1048970, // pqrstuv1048970 -> 000006136ef
)

private fun tests1() {

    for ((salt, expected) in tests1) {
        println("testing $salt (should be $expected)")

        val hasher = Hasher(salt, "0".repeat(5))
        val answer = hasher.getFirstHashWithLeadingZeros()
        println("answer $answer")

        require(answer == expected) { "wrong result; was expecting $expected, but was $answer" }
    }
}


private fun part1() {
    val result = measureTimedValue {
        val hasher = Hasher("yzbqklnj", "0".repeat(5))
        hasher.getFirstHashWithLeadingZeros()
    }

    println("part 1: ${result.value} (took ${result.duration})")
}

private fun part2() {
    val result = measureTimedValue {
        val hasher = Hasher("yzbqklnj", "0".repeat(6))
        hasher.getFirstHashWithLeadingZeros()
    }

    println("part 2: ${result.value} (took ${result.duration})")
}
