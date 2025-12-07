package util

val Int.isOdd
    get() = (this and 1) == 1

val Int.isEven
    get() = (this and 1) == 0
