package day3

import utils.readInputLines
import java.nio.file.Path

fun main() {
    val engineMap = readInputLines(Path.of("/", "day3", "input.txt"))
        .map { s -> s.toCharArray().toList() }

    val gearRatios = mutableListOf<Int>()
    for ((i, row) in engineMap.withIndex()) {
        for ((j, item) in row.withIndex()) {
            if (item == '*') {
                checkGearRatio(i, j, engineMap)?.let(gearRatios::add)
            }
        }
    }

    gearRatios.sum().let(::println)
}

private fun checkGearRatio(i: Int, j: Int, engineMap: List<List<Char>>): Int? {
    val partsNextToSymbol = mutableMapOf<Coord, Int>()

    // check top
    if (i != 0) {
        val topCenterChar = engineMap[i - 1][j]
        if (topCenterChar.isDigit()) {
            partsNextToSymbol += findNumberAt(i - 1, j, engineMap)
        }

        if (j != 0 && engineMap[i - 1][j - 1].isDigit()) {
            partsNextToSymbol += findNumberAt(i - 1, j - 1, engineMap)
        }

        if (j < engineMap[i].size - 1 && engineMap[i - 1][j + 1].isDigit()) {
            partsNextToSymbol += findNumberAt(i - 1, j + 1, engineMap)
        }
    }

    // check bottom
    if (i < engineMap.size - 1) {
        val topCenterChar = engineMap[i + 1][j]
        if (topCenterChar.isDigit()) {
            partsNextToSymbol += findNumberAt(i + 1, j, engineMap)
        }

        if (j != 0 && engineMap[i + 1][j - 1].isDigit()) {
            partsNextToSymbol += findNumberAt(i + 1, j - 1, engineMap)
        }

        if (j < engineMap[i].size - 1 && engineMap[i + 1][j + 1].isDigit()) {
            partsNextToSymbol += findNumberAt(i + 1, j + 1, engineMap)
        }
    }

    // check left
    if (j != 0) {
        val leftChar = engineMap[i][j - 1]
        if (leftChar.isDigit()) {
            partsNextToSymbol += findNumberAt(i, j - 1, engineMap)
        }
    }

    // check right
    if (j < engineMap[i].size - 1) {
        val rightChar = engineMap[i][j + 1]
        if (rightChar.isDigit()) {
            partsNextToSymbol += findNumberAt(i, j + 1, engineMap)
        }
    }

    return if (partsNextToSymbol.values.size == 2) {
        partsNextToSymbol.values.elementAt(0) * partsNextToSymbol.values.elementAt(1)
    } else {
        null
    }
}

private fun findNumberAt(i: Int, j: Int, engineMap: List<List<Char>>): Pair<Coord, Int> {
    require(engineMap[i][j].isDigit())
    var leftIndex = j
    var rightIndex = j + 1
    while (leftIndex - 1 >= 0 && engineMap[i][leftIndex - 1].isDigit()) {
        leftIndex--
    }

    while (rightIndex < engineMap[i].size && engineMap[i][rightIndex].isDigit()) {
        rightIndex++
    }

    return Coord(i, leftIndex) to engineMap[i].subList(leftIndex, rightIndex)
        .toCharArray()
        .concatToString()
        .toInt()
}
