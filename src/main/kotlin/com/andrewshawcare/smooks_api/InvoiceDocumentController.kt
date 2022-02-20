package com.andrewshawcare.smooks_api

import org.milyn.edi.unedifact.d93a.D93AInterchangeFactory
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class InvoiceDocumentController {
    @PostMapping("/invoice-document")
    fun bindInvoiceDocument(
        @RequestParam("invoiceDocument")
        invoiceDocumentMultipartFile: MultipartFile
    ): UNEdifactInterchange {
        val d93aInterchangeFactory = D93AInterchangeFactory.getInstance()
        return d93aInterchangeFactory.fromUNEdifact(invoiceDocumentMultipartFile.inputStream) as UNEdifactInterchange41
    }
}