package com.andrewshawcare.smooks_api

import org.milyn.SmooksException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class PurchaseOrderDocumentController {
    @PostMapping("/purchase-order-document")
    fun bindPurchaseOrderDocument(@RequestParam("purchaseOrderDocument") purchaseOrderDocumentMultipartFile: MultipartFile): PurchaseOrderDocument {
        try {
            return EdiDocumentService("smooks_resource_list.xml")
                .bind(
                    inputStream = purchaseOrderDocumentMultipartFile.inputStream,
                    clazz = PurchaseOrderDocument::class.java
                )
        } catch (smooksException: SmooksException) {
            throw smooksException.cause!!
        }
    }
}