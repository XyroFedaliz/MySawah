package com.mysawah.predict.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mysawah.predict.R
import com.mysawah.predict.domain.model.HistoryDetail
import com.mysawah.predict.domain.model.HistoryOrder
import com.mysawah.predict.domain.model.ProductRequest
import com.mysawah.predict.ui.components.OrderHistoryCard
import com.mysawah.predict.ui.navigation.BottomNavBar
import com.mysawah.predict.ui.theme.*


@Composable
fun PesananScreen(
    navController: NavHostController,
    onNavigateAi: () -> Unit = {},
    selectedIndex: Int = 2
) {
    val viewModel: OrderViewModel = viewModel()
    val context = LocalContext.current

    // State dari ViewModel
    val historyList by viewModel.historyList.collectAsState()
    val loading by viewModel.loading.collectAsState()

    // Ambil data saat screen dibuka
    LaunchedEffect(Unit) {
        viewModel.getHistory(context)
    }

    // Panggil UI Stateless dan oper datanya
    PesananScreenContent(
        navController = navController,
        historyList = historyList,
        loading = loading,
        onNavigateAi = onNavigateAi,
        selectedIndex = selectedIndex
    )
}

@Composable
fun PesananScreenContent(
    navController: NavHostController,
    historyList: List<HistoryOrder>,
    loading: Boolean,
    onNavigateAi: () -> Unit,
    selectedIndex: Int
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, selectedIndex = selectedIndex) },
        containerColor = BackgroundWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(12.dp))

            // TOP BAR
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Pesanan Anda",
                    style = Typography.titleLarge,
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = onNavigateAi,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(42.dp)
                        .background(PrimaryColor, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ai),
                        contentDescription = "AI",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            // LOADING
            when {
                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                }

                historyList.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Belum ada riwayat pesanan", color = Color.Gray)
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(historyList) { order ->
                            OrderHistoryCard(order)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PesananScreenPreview_Data() {
    // Dummy Data Produk
    val dummyProduct = ProductRequest(
        id_produk = 1,
        nama_produk = "Pupuk Urea",
        harga = 50000,
        kategori = "Pupuk",
        stok = 10,
        deskripsi = "Pupuk berkualitas",
        gambar_produk = ""
    )

    // Dummy Data Detail
    val dummyDetail = HistoryDetail(
        jumlah = 2,
        hargaSaatBeli = 50000,
        produk = dummyProduct
    )

    // Dummy Data Order (List)
    val dummyList = listOf(
        HistoryOrder(
            idOrder = 101,
            totalHarga = 100000,
            statusOrder = "PAID",
            tanggalTransaksi = "2025-12-06 14:30:00",
            details = listOf(dummyDetail)
        ),
        HistoryOrder(
            idOrder = 102,
            totalHarga = 250000,
            statusOrder = "PENDING",
            tanggalTransaksi = "2025-12-07 09:15:00",
            details = listOf(dummyDetail, dummyDetail)
        )
    )

    MySawahTheme {
        PesananScreenContent(
            navController = rememberNavController(),
            historyList = dummyList,
            loading = false,
            onNavigateAi = {},
            selectedIndex = 2
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PesananScreenPreview_Kosong() {
    MySawahTheme {
        PesananScreenContent(
            navController = rememberNavController(),
            historyList = emptyList(),
            loading = false,
            onNavigateAi = {},
            selectedIndex = 2
        )
    }
}