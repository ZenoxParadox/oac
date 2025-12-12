@file:Suppress("SpellCheckingInspection")

package year2015

import util.findInWindowed
import util.findInWindowedIndexed
import util.readFile
import java.io.BufferedReader
import kotlin.time.measureTimedValue

private val tests1 = mapOf(
    "ugknbfddgicrmopn" to true, // is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings
    "aaa" to true, // is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
    "jchzalrnumimnmhp" to false, // is naughty because it has no double letter.
    "haegwjzuvuyypxyu" to false, // is naughty because it contains the string xy.
    "dvszwmarrgswjxmb" to false, // is naughty because it contains only one vowel.
)

private val tests2 = mapOf(
    "qjhvhtzxzqqjkmpb" to true,     // is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
    "xxyxx" to true,                // is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
    "aaaa" to true,

    "abcdefghijklmnxabcdefghi" to false,

    "uurcxstgmygtbstg" to false,    // is naughty because it has a pair (tg) but no repeat with a single letter between them.
    "ieodomkazucvgmuy" to false,    // is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.

    "abcdefghijklmnop" to false,
    "xy123456789xy" to false,
)

fun main() {
    //tests1(1, tests1)
    tests2()

    val input = readFile("/year2015/day5.txt")

    //part1(input)
    part2(input)
}


private class NaughtyChecker {

    private fun containsEnoughVowels(sequence: String): Boolean {
        val vowelCount = sequence.count { it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u' }
        println("vowelCount: $vowelCount")

        return vowelCount >= 3
    }

    private fun containsDuplicateLetters(sequence: String): Boolean {
        for (i in 0..sequence.length - 2) {
            val window = sequence.substring(i, i + 2)

            if (window[0] == window[1]) {
                println("Contains duplicate letter: ${window[0]}")
                return true
            }
        }

        return false
    }

    private fun isFreeOrIllegalSequence(sequence: String): Boolean {

        if (sequence.contains("ab") || sequence.contains("cd") || sequence.contains("pq") || sequence.contains("xy")) {
            println("contains illegal combination.")

            return false
        }

        return true
    }

    fun isNice(sequence: String): Boolean {
        // It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
        if (containsEnoughVowels(sequence)) {
            //println("contains enough vowels..")

            // It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
            if (containsDuplicateLetters(sequence)) {
                println("contains duplicate letters..")

                // It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
                if (isFreeOrIllegalSequence(sequence)) {
                    println("free of illegal sequences..")

                    return true
                }
            }
        }

        // if not nice, then naughty
        return false
    }


}

private class NaughtyChecker2 {

    private fun hasLetterPairRepeating(sequence: String): Boolean {
        return sequence.findInWindowedIndexed(2) { i, pair ->
            println("pair $pair (index: $i and ${i + 1})")

            val otherIndex = sequence.indexOf(pair, i + 2)

            if (otherIndex != -1) {
                println("found repeated (other index $otherIndex/${sequence.length-1}).")
            }

            otherIndex != -1
        }
    }

    private fun hasLetterPairWithOneInBetween(sequence: String): Boolean {
        println("hasLetterPairWithOneInBetween($sequence)")

        return sequence.findInWindowed(3) { chars ->
            val isTrue = chars[0] == chars[2]
            println("$chars -> $isTrue")
            isTrue
        }
    }


    fun isNice(sequence: String): Boolean {

        // It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
        if (hasLetterPairRepeating(sequence)) {
            // It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
            if (hasLetterPairWithOneInBetween(sequence)) {
                return true
            }
        }

        // if not nice, then naughty
        return false
    }


}

private fun tests1() {
    println("tests part 1 cases)")

    for ((sequence, expected) in tests1) {
        println("testing $sequence (should be $expected) -----------------------------")

        val checker = NaughtyChecker()
        val isNice = checker.isNice(sequence)
        println("isNice $isNice")

        require(isNice == expected) { "wrong result; was expecting $expected, but was $isNice" }
    }
}

private fun tests2() {
    println("tests part 2 cases)")

    for ((sequence, expected) in tests2) {
        println("testing $sequence (should be $expected) ------------------------------")

        val checker = NaughtyChecker2()
        val isNice = checker.isNice(sequence)
        println("isNice $isNice")

        require(isNice == expected) { "wrong result; was expecting $expected, but was $isNice" }
    }
}


private fun part1(reader: BufferedReader) {

    var counter = 0
    val checker = NaughtyChecker()

    val result = measureTimedValue {
        reader.forEachLine {
            if (checker.isNice(it)) {
                counter++
            }
        }

        counter
    }

    println("part 1: ${result.value} (took ${result.duration})")
}

private fun part2(reader: BufferedReader) {

    var counter = 0
    val checker = NaughtyChecker2()

    val result = measureTimedValue {
        reader.forEachLine {
            if (checker.isNice(it)) {
                counter++
            }
        }

        counter
    }

    println("part 2: ${result.value} (took ${result.duration})")
}
