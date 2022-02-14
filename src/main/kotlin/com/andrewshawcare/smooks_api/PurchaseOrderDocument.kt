package com.andrewshawcare.smooks_api

data class PurchaseOrderDocument(
    var purchaseOrderHeader: PurchaseOrderHeader = PurchaseOrderHeader(),
    var purchaseOrderCustomer: PurchaseOrderCustomer = PurchaseOrderCustomer(),
    var purchaseOrderItemCollection: Collection<PurchaseOrderItem> = mutableListOf()
)