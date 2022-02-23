package com.andrewshawcare.smooks_api

import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EdifactDocumentController {
    private val unEdifactMessageService = UNEdifactMessageService()

    @PostMapping("/edifact-document")
    fun createEdifactDocument(
        @RequestParam("unEdifactVersionNumberAndReleaseNumber")
        unEdifactVersionNumberAndReleaseNumber: String,
        @RequestParam("unEdifactMessageId")
        unEdifactMessageId: String,
        @RequestParam("unEdifactMessageMultipartFile")
        unEdifactMessageMultipartFile: MultipartFile
    ): UNEdifactInterchange {
        return unEdifactMessageService.fromEdi(
            unEdifactVersionNumberAndReleaseNumber = UNEdifactVersionNumberAndReleaseNumber.valueOf(unEdifactVersionNumberAndReleaseNumber),
            unEdifactMessageId = unEdifactMessageId,
            unEdifactMessageInputStream = unEdifactMessageMultipartFile.inputStream
        )
    }
}