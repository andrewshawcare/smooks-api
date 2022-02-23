package com.andrewshawcare.smooks_api

fun mapToLogfmtMessage(map: Map<String, String>): String {
    return map.entries.map { "${it.key}=${it.value}" }.joinToString(separator = " ")
}