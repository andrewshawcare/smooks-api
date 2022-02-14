package com.andrewshawcare.smooks_api

import java.math.BigDecimal
import java.util.*

data class PurchaseOrderHeader(
    var id: String = "",
    var statusCode: String = "",
    var netAmount: BigDecimal = BigDecimal.ZERO,
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    var tax: BigDecimal = BigDecimal.ZERO,
    var date: Date = Date()
)