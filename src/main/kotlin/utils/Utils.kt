package utils

import java.io.InputStream
import java.nio.file.Path

private object ResourceAccess

fun readInputLines(path: Path) = getResourceAsStream(path)
    .bufferedReader()
    .readLines()

fun readInputLineSequence(path: Path) = getResourceAsStream(path)
    .bufferedReader()
    .lineSequence()

fun getResourceAsStream(path: Path): InputStream =
    ResourceAccess::class.java.getResourceAsStream(path.toString()) ?: error("Resource at path not found: $path")