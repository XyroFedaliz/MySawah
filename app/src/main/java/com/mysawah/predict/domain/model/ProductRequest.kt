package com.mysawah.predict.domain.model

data class ProductRequest(
    val id_produk: Int,
    val nama_produk: String,
    val harga: Int,
    val stok: Int,
    val kategori: String,
    val deskripsi: String,
    val gambar_produk: String
)
