package day4

import utils.readInputLineSequence
import java.nio.file.Path
import java.util.*

data class IndexedCard(
    val index: Int,
    val card: Card
) : Comparable<IndexedCard> {
    override fun compareTo(other: IndexedCard): Int {
        return index.compareTo(other.index)
    }
}

fun main() {
    val cards = readInputLineSequence(Path.of("/", "day4", "input.txt"))
        .mapIndexed { index, line ->
            val (winningNumbersPart, myNumbersPart) = line.substringAfter(": ")
                .split(" | ")

            val winningNumbers = winningNumbersPart.split(" ")
                .filter(String::isNotEmpty)
                .map(Integer::valueOf)

            val myNumbers = myNumbersPart.split(" ")
                .filter(String::isNotEmpty)
                .map(Integer::valueOf)

            IndexedCard(index, Card(winningNumbers.toSet(), myNumbers.toSet()))
        }
        .toList()

    var count = 0
    val cardPriorityQueue = PriorityQueue(cards)
    while (cardPriorityQueue.isNotEmpty()) {
        val currentCard = cardPriorityQueue.remove()
        count++

        cards.drop(currentCard.index + 1)
            .take(currentCard.points())
            .let(cardPriorityQueue::addAll)
    }

    println(count)
}

fun IndexedCard.points() = (card.winningNumbers intersect card.myNumbers).size
