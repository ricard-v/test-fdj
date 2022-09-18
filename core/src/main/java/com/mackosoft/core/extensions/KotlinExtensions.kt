package com.mackosoft.core.extensions

fun <T, U> takeIfNotNull(first: T?, second: U?): Pair<T, U>? =
    if (first != null && second != null) {
        Pair(first, second)
    } else {
        null
    }