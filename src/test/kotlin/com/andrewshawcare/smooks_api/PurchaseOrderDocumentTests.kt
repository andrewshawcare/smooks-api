package com.andrewshawcare.smooks_api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.util.*

class PurchaseOrderDocumentTests {
    @Test
    fun purchaseOrderDocumentFromEdi() {
        val ediDocumentMapper = EdiDocumentMapper("smooks_resource_list.xml")
        val actualPurchaseOrderDocument = ediDocumentMapper.bind(
            inputStream = File("src/test/resources/purchase_order.edi").inputStream(),
            clazz = PurchaseOrderDocument::class.java
        )
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("EST"), Locale.ENGLISH)
        calendar.set(
            2006,
            Calendar.NOVEMBER,
            15,
            13,
            45,
            28
        )
        calendar.set(Calendar.MILLISECOND, 0)
        val expectedPurchaseOrderDocument = PurchaseOrderDocument(
            purchaseOrderHeader = PurchaseOrderHeader(
                id = "1",
                statusCode = "0",
                netAmount = BigDecimal.valueOf(59.97),
                totalAmount = BigDecimal.valueOf(64.92),
                tax = BigDecimal.valueOf(4.95),
                date = calendar.time
            ),
            purchaseOrderCustomer = PurchaseOrderCustomer(
                username = "user1",
                name = Name(
                    first = "Harry",
                    last = "Fletcher"
                ),
                state = "SD"
            ),
            purchaseOrderItemCollection = listOf(
                PurchaseOrderItem(
                    position = 1,
                    quantity = 1,
                    productId = "364",
                    title = "The 40-Year-Old Virgin",
                    price = BigDecimal.valueOf(29.98)
                ),
                PurchaseOrderItem(
                    position = 2,
                    quantity = 1,
                    productId = "299",
                    title = "Pulp Fiction",
                    price = BigDecimal.valueOf(29.99)
                )
            )
        )
        assertEquals(expectedPurchaseOrderDocument, actualPurchaseOrderDocument)
    }
}