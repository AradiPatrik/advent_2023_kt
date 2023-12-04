package day4

import utils.readInputLineSequence
import java.math.BigInteger
import java.nio.file.Path

fun main() {
    val cards = readInputLineSequence(Path.of("/", "day4", "input.txt"))
        .map { line ->
            val (winningNumbersPart, myNumbersPart) = line.substringAfter(": ")
                .split(" | ")

            val winningNumbers = winningNumbersPart.split(" ")
                .filter(String::isNotEmpty)
                .map(Integer::valueOf)

            val myNumbers = myNumbersPart.split(" ")
                .filter(String::isNotEmpty)
                .map(Integer::valueOf)

            Card(winningNumbers.toSet(), myNumbers.toSet())
        }
        .toList()

    val points = cards
        .map(Card::points)

    points.sum()
        .let(::println)
}

private fun Card.points() = (winningNumbers intersect myNumbers).size.let {
    if (it > 0) {
        BigInteger.valueOf(2).pow(it - 1).toInt()
    } else {
        0
    }
}