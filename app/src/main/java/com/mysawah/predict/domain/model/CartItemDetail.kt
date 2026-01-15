package com.mysawah.predict.domain.model

data class CartItemDetail(
    val product: ProductRequest,
    var qty: Int
)