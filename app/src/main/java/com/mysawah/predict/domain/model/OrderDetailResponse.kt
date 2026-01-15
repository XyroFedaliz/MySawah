package com.mysawah.predict.domain.model

data class OrderDetailResponse(
    val id_detail: Int,
    val id_produk: Int,
    val jumlah: Int,
    val harga_saat_beli: Int,
    val produk: ProductRequest
)