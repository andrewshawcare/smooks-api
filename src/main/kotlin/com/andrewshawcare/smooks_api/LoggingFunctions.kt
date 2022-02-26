package com.andrewshawcare.smooks_api

fun mapToLogfmtMessage(map: Map<String, String>): String {
    return map.entries.joinToString(separator = " ") {
        "${it.key}=\"${it.value.replace("\"", "'")}\""
    }
}