package com.andrewshawcare.smooks_api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PurchaseOrderDocumentControllerTests(
    @Autowired val restTemplate: TestRestTemplate
) {
    @Test
    fun bindPurchaseOrder() {
        val actualPurchaseOrderDocument = restTemplate.postForEntity<PurchaseOrderDocument>("/purchase-order-document").body
        assertEquals(PurchaseOrderDocument(), actualPurchaseOrderDocument)
    }
}