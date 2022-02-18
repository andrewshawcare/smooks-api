package com.andrewshawcare.smooks_api

import org.milyn.SmooksException
import org.milyn.javabean.DataDecodeException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import com.andrewshawcare.smooks_api.model.PurchaseOrderDocument

@RestController
class PurchaseOrderDocumentController {
    @PostMapping("/purchase-order-document")
    fun bindPurchaseOrderDocument(@RequestParam("purchaseOrderDocument") purchaseOrderDocumentMultipartFile: MultipartFile): PurchaseOrderDocument {
        try {
            return com.andrewshawcare.smooks_api.model.PurchaseOrderDocumentFactory
                .getInstance()
                .fromEDI(purchaseOrderDocumentMultipartFile.inputStream)
        } catch (smooksException: SmooksException) {
            var cause: Throwable = smooksException.cause!!
            var message = ""

            while (cause is DataDecodeException) {
                message += "${cause.localizedMessage}\n"
                cause = cause.cause!!
            }

            throw Exception(message + cause.localizedMessage)
        }
    }
}