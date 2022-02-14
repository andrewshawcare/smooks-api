package com.andrewshawcare.smooks_api

import org.milyn.Smooks
import org.milyn.payload.JavaResult
import java.io.InputStream
import javax.xml.transform.stream.StreamSource

class EdiDocumentMapper(configurationResourceURI: String) {
    private val smooks: Smooks

    init {
        smooks = Smooks(configurationResourceURI)
    }

    fun <T> bind(inputStream: InputStream, clazz: Class<T>): T {
        val executionContext = smooks.createExecutionContext()
        val javaResult = JavaResult()
        smooks.filterSource(executionContext, StreamSource(inputStream), javaResult)
        return javaResult.getBean(clazz)
    }
}