package day2

import utils.readInputLineSequence
import java.nio.file.Path
import kotlin.math.max

data class Round(
    val red: Int,
    val green: Int,
    val blue: Int,
)

data class Game(
    val id: Int,
    val rounds: List<Round>
)

fun main() {
    readInputLineSequence(Path.of("/", "day2", "input.txt"))
        .map(::convertLineToGame)
        .filter(::isGamePossible)
        .sumOf(Game::id)
        .let(::println)

    readInputLineSequence(Path.of("/", "day2", "input.txt"))
        .map(::convertLineToGame)
        .sumOf(Game::power)
        .let(::println)
}

private fun Game.power() = rounds
    .reduce { acc, curr ->
        Round(
            red = max(acc.red, curr.red),
            green = max(acc.green, curr.green),
            blue = max(acc.blue, curr.blue)
        )
    }
    .let { round -> round.red * round.green * round.blue }

private fun isGamePossible(game: Game) = game.rounds.all { round ->
    round.blue <= 14 && round.green <= 13 && round.red <= 12
}

private fun convertLineToGame(line: String): Game {
    val (gamePart, roundsPart) = line.split(": ")
    val gameId = gamePart.split(" ")[1].toInt()

    val roundParts = roundsPart.split("; ")
    val rounds = roundParts.map { roundPart ->
        val cubeParts = roundPart.split(", ")
        var redCount = 0
        var greenCount = 0
        var blueCount = 0
        for (cubePart in cubeParts) {
            val (cubeCount, cubeColor) = cubePart.split(" ")
            when (cubeColor) {
                "red" -> redCount = cubeCount.toInt()
                "green" -> greenCount = cubeCount.toInt()
                "blue" -> blueCount = cubeCount.toInt()
            }
        }
        Round(redCount, greenCount, blueCount)
    }

    return Game(
        gameId,
        rounds
    )
}