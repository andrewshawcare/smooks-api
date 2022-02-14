package com.andrewshawcare.smooks_api

import java.math.BigDecimal

data class PurchaseOrderItem(
    var position: Int = 0,
    var quantity: Int = 0,
    var productId: String = "",
    var title: String = "",
    var price: BigDecimal = BigDecimal.ZERO
)