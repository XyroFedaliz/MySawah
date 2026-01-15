package com.mysawah.predict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysawah.predict.domain.model.HistoryOrder
import androidx.compose.ui.tooling.preview.Preview
import com.mysawah.predict.domain.model.HistoryDetail
import com.mysawah.predict.ui.theme.MySawahTheme
import com.mysawah.predict.domain.model.ProductRequest
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun OrderHistoryCard(order: HistoryOrder) {

    val (dateStr, timeStr) = parseDateTime(order.tanggalTransaksi)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6E6))
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFBFBFBF))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tanggal pemesanan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = order.statusOrder,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (order.statusOrder == "PAID") Color(0xFF4D512F) else Color.Red
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = dateStr, fontSize = 16.sp)
                    Text(text = timeStr, fontSize = 14.sp)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                // Loop dari details API
                order.details.forEach { detail ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = detail.produk.nama_produk, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = detail.produk.kategori, fontSize = 12.sp, color = Color.Gray)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "${detail.jumlah}x", fontWeight = FontWeight.Bold)
                            Text(text = "Rp ${formatToRupiah(detail.hargaSaatBeli)}", fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 🔹 TOTAL
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Pembayaran :",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp ${formatToRupiah(order.totalHarga)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


fun formatToRupiah(value: Int): String {
    return "%,d".format(value).replace(",", ".")
}

fun parseDateTime(serverDate: String): Pair<String, String> {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateObj = parser.parse(serverDate)

        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        Pair(dateFormatter.format(dateObj!!), timeFormatter.format(dateObj))
    } catch (_: Exception) {
        Pair(serverDate, "")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderHistoryCard() {
    // Dummy Product
    val dummyProduct1 = ProductRequest(
        id_produk = 1,
        nama_produk = "Pupuk Urea",
        harga = 50000,
        kategori = "Pupuk",
        stok = 100,
        deskripsi = "Pupuk penyubur",
        gambar_produk = ""
    )

    val dummyProduct2 = ProductRequest(
        id_produk = 2,
        nama_produk = "Bibit Padi IR64",
        harga = 120000,
        kategori = "Bibit",
        stok = 50,
        deskripsi = "Bibit unggul",
        gambar_produk = ""
    )

    // Dummy Detail
    val detailsList = listOf(
        HistoryDetail(jumlah = 2, hargaSaatBeli = 50000, produk = dummyProduct1),
        HistoryDetail(jumlah = 1, hargaSaatBeli = 120000, produk = dummyProduct2)
    )

    // Dummy Order
    val dummyOrderPaid = HistoryOrder(
        idOrder = 101,
        totalHarga = 220000,
        statusOrder = "PAID",
        tanggalTransaksi = "2025-12-20 14:30:00",
        details = detailsList
    )

    val dummyOrderPending = HistoryOrder(
        idOrder = 102,
        totalHarga = 50000,
        statusOrder = "PENDING",
        tanggalTransaksi = "2025-12-21 09:15:00",
        details = listOf(
            HistoryDetail(jumlah = 1, hargaSaatBeli = 50000, produk = dummyProduct1)
        )
    )

    MySawahTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Contoh 1: Lunas
            OrderHistoryCard(order = dummyOrderPaid)

            Spacer(modifier = Modifier.height(20.dp))

            // Contoh 2: Belum Bayar / Pending
            OrderHistoryCard(order = dummyOrderPending)
        }
    }
}
