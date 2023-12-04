package day1

import utils.getResourceAsStream
import utils.readInputLineSequence
import java.nio.file.Path
import kotlin.time.measureTimedValue

private val digitTextToValue = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
)

private fun convertToNumber(line: String): Int {
    val firstAndLastOccurrences = digitTextToValue
        .keys
        .mapNotNull { key ->
            line.indexOf(key)
                .takeIf { it != -1 }
                ?.let { key to it }
        }
        .map { (key, firstOccurrence) ->
            Triple(key, firstOccurrence, line.lastIndexOf(key))
        }

    val firstDigit = firstAndLastOccurrences.minBy { (_, firstOccurrence, _) -> firstOccurrence }
        .let { (key, _, _) -> digitTextToValue[key].toString() }
    val lastDigit = firstAndLastOccurrences.maxBy { (_, _, lastOccurrence) -> lastOccurrence }
        .let { (key, _, _) -> digitTextToValue[key].toString() }

    return "$firstDigit$lastDigit".toInt()
}

private fun convertToNumber2(line: String): Int {
    var firstDigitIndex = Int.MAX_VALUE
    var lastDigitIndex = Int.MIN_VALUE

    var firstDigit = 0
    var lastDigit = 0

    for ((key, value) in digitTextToValue.entries) {
        val firstIndex = line.indexOf(key)
        if (firstIndex == -1) {
            continue
        }
        val lastIndex = line.lastIndexOf(key)

        if (firstIndex < firstDigitIndex) {
            firstDigitIndex = firstIndex
            firstDigit = value
        }

        if (lastIndex > lastDigitIndex) {
            lastDigitIndex = lastIndex
            lastDigit = value
        }
    }

    return "$firstDigit$lastDigit".toInt()
}

fun main() {
    measureTimedValue {
        readInputLineSequence(Path.of("/", "day1", "input.txt"))
            .map(::convertToNumber2)
            .sum()
    }.let { (value, time) ->
        println(time.inWholeMilliseconds / 1000f)
        println(value)
    }
}