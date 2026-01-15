package com.mysawah.predict.domain.model

import com.google.gson.annotations.SerializedName

data class HistoryDetail(
    val jumlah: Int,
    @SerializedName("harga_saat_beli") val hargaSaatBeli: Int,
    val produk: ProductRequest // Pastikan model ProductRequest sudah ada di package ini
)