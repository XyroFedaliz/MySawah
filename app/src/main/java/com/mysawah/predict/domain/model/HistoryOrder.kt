package com.mysawah.predict.domain.model

import com.google.gson.annotations.SerializedName

data class HistoryOrder(
    @SerializedName("id_orders") val idOrder: Int,
    @SerializedName("total_harga") val totalHarga: Int,
    @SerializedName("status_order") val statusOrder: String,
    @SerializedName("tanggal_transaksi") val tanggalTransaksi: String,
    @SerializedName("details") val details: List<HistoryDetail>
)