package ru.countermeasure.moviestvshowsdb.extension

fun String.truncate(size: Int = 300): String {
    require(size >= 0) { "Size must be non-negative, was $size" }

    val trimmed = this.trimEnd()
    if (trimmed.length <= size)
        return trimmed
    return "${this.subSequence(0, size).trimEnd()}..."
}