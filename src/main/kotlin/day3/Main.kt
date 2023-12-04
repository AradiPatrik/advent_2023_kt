package day3

import utils.readInputLines
import java.nio.file.Path

fun main() {
    val engineMap = readInputLines(Path.of("/", "day3", "input.txt"))
        .map { s -> s.toCharArray().toList() }

    val partMap = HashMap<Coord, Int>()
    for ((i, row) in engineMap.withIndex()) {
        for ((j, item) in row.withIndex()) {
            if (!item.isDigit() && item != '.') {
                partMap.putAll(findPartsNextToSymbol(i, j, engineMap))
            }
        }
    }

    partMap.values.sum().let(::println)
}

private fun findPartsNextToSymbol(i: Int, j: Int, engineMap: List<List<Char>>): List<Pair<Coord, Int>> {
    val partsNextToSymbol = mutableListOf<Pair<Coord, Int>>()

    // check top
    if (i != 0) {
        val topCenterChar = engineMap[i - 1][j]
        if (topCenterChar.isDigit()) {
            partsNextToSymbol.add(findNumberAt(i - 1, j, engineMap))
        }

        if (j != 0 && engineMap[i - 1][j - 1].isDigit()) {
            partsNextToSymbol.add(findNumberAt(i - 1, j - 1, engineMap))
        }

        if (j < engineMap[i].size - 1 && engineMap[i - 1][j + 1].isDigit()) {
            partsNextToSymbol.add(findNumberAt(i - 1, j + 1, engineMap))
        }
    }

    // check bottom
    if (i < engineMap.size - 1) {
        val topCenterChar = engineMap[i + 1][j]
        if (topCenterChar.isDigit()) {
            partsNextToSymbol.add(findNumberAt(i + 1, j, engineMap))
        }

        if (j != 0 && engineMap[i + 1][j - 1].isDigit()) {
            partsNextToSymbol.add(findNumberAt(i + 1, j - 1, engineMap))
        }

        if (j < engineMap[i].size - 1 && engineMap[i + 1][j + 1].isDigit()) {
            partsNextToSymbol.add(findNumberAt(i + 1, j + 1, engineMap))
        }
    }

    // check left
    if (j != 0) {
        val leftChar = engineMap[i][j - 1]
        if (leftChar.isDigit()) {
            partsNextToSymbol.add(findNumberAt(i, j - 1, engineMap))
        }
    }

    // check right
    if (j < engineMap[i].size - 1) {
        val rightChar = engineMap[i][j + 1]
        if (rightChar.isDigit()) {
            partsNextToSymbol.add(findNumberAt(i, j + 1, engineMap))
        }
    }

    return partsNextToSymbol
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
