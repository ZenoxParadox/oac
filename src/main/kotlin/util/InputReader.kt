package util

import java.io.BufferedReader
import kotlin.jvm.java

fun readFile(location: String): BufferedReader {
    return {}::class.java.getResource(location)?.openStream()?.bufferedReader()!!
}
