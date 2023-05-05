package com.andrewshawcare.smooks_api

import org.json.JSONObject
import org.milyn.SmooksException
import org.milyn.javabean.DataDecodeException
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import java.io.InputStream
import java.util.logging.Level
import java.util.logging.Logger

class UNEdifactMessageService @Autowired constructor(
    private val unEdifactInterchangeFactoryStrategy: UNEdifactInterchangeFactoryStrategy,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val logger = Logger.getLogger(this::class.java.name)

    fun fromEdi(
        unEdifactVersionNumberAndReleaseNumber: UNEdifactVersionNumberAndReleaseNumber,
        unEdifactMessageId: String,
        unEdifactMessageInputStream: InputStream
    ): UNEdifactInterchange {
        logger.log(
            Level.INFO,
            JSONObject(mapOf(
                "event" to "MessageMappingStarted",
                "sourceFormat" to "EDI",
                "targetFormat" to "Java",
                "standard" to "EDIFACT",
                "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
                "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber,
                "messageId" to unEdifactMessageId
            )).toString()
        )

        val unEdifactInterchangeFactory = unEdifactInterchangeFactoryStrategy.getInstance(unEdifactVersionNumberAndReleaseNumber)
        val unEdifactInterchange: UNEdifactInterchange41
        val startTimeMillis = System.currentTimeMillis()

        try {
            unEdifactInterchange = unEdifactInterchangeFactory.fromUNEdifact(unEdifactMessageInputStream) as UNEdifactInterchange41
        } catch (smooksException: SmooksException) {
            val endTimeMillis = System.currentTimeMillis()
            val errorType = smooksException.cause!!.javaClass.simpleName

            var message = ""
            var cause: Throwable = smooksException.cause!!

            while (cause is DataDecodeException) {
                message += "${cause.localizedMessage}\n"
                cause = cause.cause!!
            }

            val errorMessages = (message + cause.localizedMessage).replace(oldChar = '\n', newChar = ',')

            logger.log(
                Level.SEVERE,
                JSONObject(mapOf(
                    "event" to "MessageMappingFailed",
                    "format" to "EDI",
                    "standard" to "EDIFACT",
                    "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
                    "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber,
                    "messageId" to unEdifactMessageId,
                    "errorType" to errorType,
                    "errorMessages" to errorMessages,
                    "duration" to (endTimeMillis - startTimeMillis).toString()
                )).toString()
            )

            throw Exception(errorMessages)
        }

        val endTimeMillis = System.currentTimeMillis()
        val messageMappingSucceededRecord = JSONObject(mapOf(
            "event" to "MessageMappingSucceeded",
            "format" to "EDI",
            "standard" to "EDIFACT",
            "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
            "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber,
            "senderId" to (unEdifactInterchange.interchangeHeader?.sender?.id ?: ""),
            "recipientId" to (unEdifactInterchange.interchangeHeader?.recipient?.id ?: ""),
            "messageId" to (unEdifactInterchange.messages?.first()?.messageHeader?.messageIdentifier?.id ?: ""),
            "duration" to (endTimeMillis - startTimeMillis).toString()
        )).toString()

        logger.log(Level.INFO, messageMappingSucceededRecord)

        kafkaTemplate.send("events", messageMappingSucceededRecord)

        return unEdifactInterchange
    }
}