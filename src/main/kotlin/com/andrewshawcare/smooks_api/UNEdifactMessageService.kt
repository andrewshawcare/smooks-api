package com.andrewshawcare.smooks_api

import org.milyn.SmooksException
import org.milyn.javabean.DataDecodeException
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41
import java.io.InputStream
import java.util.logging.Level
import java.util.logging.Logger

class UNEdifactMessageService {
    private val logger: Logger = Logger.getLogger(this::class.java.name)

    fun fromEdi(
        unEdifactVersionNumberAndReleaseNumber: UNEdifactVersionNumberAndReleaseNumber,
        unEdifactMessageInputStream: InputStream
    ): UNEdifactInterchange {
        logger.log(
            Level.INFO,
            mapToLogfmtMessage(mapOf(
                "event" to "MessageMappingStarted",
                "sourceFormat" to "EDI",
                "targetFormat" to "Java",
                "standard" to "EDIFACT",
                "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
                "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber
            ))
        )

        val unEdifactInterchangeFactory = UNEdifactInterchangeFactoryStrategy().getInstance(unEdifactVersionNumberAndReleaseNumber)

        val unEdifactInterchange: UNEdifactInterchange41

        try {
            unEdifactInterchange = unEdifactInterchangeFactory.fromUNEdifact(unEdifactMessageInputStream) as UNEdifactInterchange41
        } catch (smooksException: SmooksException) {
            var cause: Throwable = smooksException.cause!!
            var message = ""

            while (cause is DataDecodeException) {
                message += "${cause.localizedMessage}\n"
                cause = cause.cause!!
            }

            val errorMessages = (message + cause.localizedMessage).replace(oldChar = '\n', newChar = ',')

            logger.log(
                Level.SEVERE,
                mapToLogfmtMessage(mapOf(
                    "event" to "MessageMapppingFailed",
                    "format" to "EDI",
                    "standard" to "EDIFACT",
                    "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
                    "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber,
                    "errorMessages" to errorMessages
                ))
            )

            throw Exception(errorMessages)
        }

        logger.log(
            Level.INFO,
            mapToLogfmtMessage(mapOf(
                "event" to "MessageMappingSucceeded",
                "format" to "EDI",
                "standard" to "EDIFACT",
                "versionNumber" to unEdifactVersionNumberAndReleaseNumber.versionNumber,
                "releaseNumber" to unEdifactVersionNumberAndReleaseNumber.releaseNumber,
                "senderId" to (unEdifactInterchange.interchangeHeader?.sender?.id ?: ""),
                "recipientId" to (unEdifactInterchange.interchangeHeader?.recipient?.id ?: ""),
                "messageId" to (unEdifactInterchange.messages?.first()?.messageHeader?.messageIdentifier?.id ?: "")
            ))
        )

        return unEdifactInterchange
    }
}