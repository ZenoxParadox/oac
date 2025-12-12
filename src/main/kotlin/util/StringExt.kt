package util

fun String.findInWindowed(
    windowSize: Int,
    predicate: (String) -> Boolean
): Boolean {
    for (i in 0..this.length - windowSize) {
        val window = this.substring(i, i + windowSize)
        if (predicate(window)) {
            return true
        }
    }
    return false
}

fun String.findInWindowedIndexed(
    windowSize: Int,
    predicate: (Int, String) -> Boolean
): Boolean {
    for (i in 0..this.length - windowSize) {
        val window = this.substring(i, i + windowSize)
        if (predicate(i, window)) {
            return true
        }
    }
    return false
}
