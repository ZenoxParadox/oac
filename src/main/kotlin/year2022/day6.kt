package year2022

/*

(0), the initial state before any insertions.
0 (1): the spinlock steps forward three times (0, 0, 0), and then inserts the first value, 1, after it. 1 becomes the current position.
0 (2) 1: the spinlock steps forward three times (0, 1, 0), and then inserts the second value, 2, after it. 2 becomes the current position.
0  2 (3) 1: the spinlock steps forward three times (1, 0, 2), and then inserts the third value, 3, after it. 3 becomes the current position.

And so on:
0  2 (4) 3  1
0 (5) 2  4  3  1
0  5  2  4  3 (6) 1
0  5 (7) 2  4  3  6  1
0  5  7  2  4  3 (8) 6  1
0 (9) 5  7  2  4  3  8  6  1

 */


fun main(){

    val list = mutableListOf(2017)
    list[0] = 0
    var counter = 1
    var index = 0
    val input = 367

    println("Begin $list")

    repeat(2017) {
        //println("Step $step ---------------------")

        // move 3 times
        index = ((input + index) % list.size) + 1


        list.add(index, counter++)
        //println("result for step [$step] $list")
    }

    println("done $list")
}
