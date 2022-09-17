package com.mackosoft.core.test.utils

import java.io.File

object JsonReaderUtils {
    fun getJsonFromPath(path: String): String {
        val uri = this::class.java.classLoader!!.getResource(path)
        val file = File(uri.path.replace("%20", " ")) // handle whitespaces in paths
        return String(file.readBytes())
    }
}