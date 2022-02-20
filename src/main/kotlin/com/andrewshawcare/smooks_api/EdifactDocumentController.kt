package com.andrewshawcare.smooks_api

import org.milyn.edi.unedifact.d01b.D01BInterchangeFactory
import org.milyn.edi.unedifact.d96a.D96AInterchangeFactory
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchangeFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EdifactDocumentController {
    private val edifactReleaseNumberToUNEdifactInterchangeFactory = mapOf<String, UNEdifactInterchangeFactory>(
        "96A" to D96AInterchangeFactory.getInstance(),
        "01B" to D01BInterchangeFactory.getInstance()
    )
    @PostMapping("/edifact-document")
    fun createEdifactDocument(
        @RequestParam("edifactReleaseNumber")
        edifactReleaseNumber: String,
        @RequestParam("edifactMessageMultipartFile")
        edifactMessageMultipartFile: MultipartFile
    ): UNEdifactInterchange {
        return edifactReleaseNumberToUNEdifactInterchangeFactory[edifactReleaseNumber]
            ?.fromUNEdifact(edifactMessageMultipartFile.inputStream) ?: throw Exception()
    }
}