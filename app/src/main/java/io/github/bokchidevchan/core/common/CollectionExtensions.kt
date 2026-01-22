package io.github.bokchidevchan.core.common

fun <T> List<T>.safeGet(index: Int): T? {
    return if (index in indices) this[index] else null
}

fun <T> List<T>.replaceAt(index: Int, item: T): List<T> {
    return toMutableList().apply {
        if (index in indices) this[index] = item
    }
}

fun <T> List<T>.removeAt(index: Int): List<T> {
    return toMutableList().apply {
        if (index in indices) removeAt(index)
    }
}

fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: () -> V): V {
    return this[key] ?: defaultValue()
}

inline fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val index = indexOfFirst(predicate)
    return if (index >= 0) index else null
}
