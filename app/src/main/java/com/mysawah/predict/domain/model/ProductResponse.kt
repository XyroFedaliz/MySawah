package com.mysawah.predict.domain.model

data class ProductResponse(
    val status: String,
    val message: String,
    val data: List<ProductRequest>
)
