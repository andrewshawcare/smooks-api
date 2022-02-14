package com.andrewshawcare.smooks_api

data class PurchaseOrderCustomer(
    var username: String = "",
    var name: Name = Name(),
    var state: String = ""
)