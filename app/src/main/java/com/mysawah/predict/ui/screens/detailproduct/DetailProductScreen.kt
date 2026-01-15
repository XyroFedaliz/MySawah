package com.mysawah.predict.ui.screens.detailproduct

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // <--- PENTING
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mysawah.predict.R
import com.mysawah.predict.data.repository.CartRepository // <--- PENTING
import com.mysawah.predict.ui.components.FloatingCartButton // <--- PENTING
import com.mysawah.predict.ui.components.ProductCard
import com.mysawah.predict.ui.theme.*
import kotlinx.coroutines.launch // <--- PENTING

@Composable
fun DetailProductScreen(
    productId: Int,
    navController: NavHostController,
    onAiClick: () -> Unit = {},
    viewModel: DetailProductViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // State Data
    var search by remember { mutableStateOf("") }
    var expandDescription by remember { mutableStateOf(false) }

    val product by viewModel.product.collectAsState()
    val otherProducts by viewModel.otherProducts.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cart State Floating Button
    val cartItems by CartRepository.cartItems.collectAsState()

    LaunchedEffect(productId) {
        viewModel.setProductId(productId)
    }

    // UI STATE GUARD
    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
            return
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $error")
            }
            return
        }

        product == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Produk tidak ditemukan")
            }
            return
        }
    }

    Scaffold(
        containerColor = BackgroundWhite,
        // Tombol Floating
        floatingActionButton = {
            FloatingCartButton(
                visible = cartItems.isNotEmpty(),
                onClick = { navController.navigate("cart") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Spacer(Modifier.height(5.dp)) }

            // Search Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = {
                            Text("Cari sesuatu", fontSize = 14.sp, color = PrimaryColor)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null, tint = PrimaryColor)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(40.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFD9D9D9),
                            unfocusedContainerColor = Color(0xFFD9D9D9),
                            cursorColor = PrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = onAiClick,
                        modifier = Modifier
                            .size(50.dp)
                            .background(PrimaryColor, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ai),
                            contentDescription = "AI",
                            tint = Color.White
                        )
                    }
                }
            }

            // Detail Product
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEDEDED), RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .width(130.dp)
                            .height(160.dp)
                    ) {
                        AsyncImage(
                            model = product!!.gambar_produk,
                            contentDescription = product!!.nama_produk,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            product!!.nama_produk,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        DetailTextRow("Kategori", product!!.kategori)
                        DetailTextRow("Harga", "Rp ${product!!.harga}")
                        DetailTextRow("Stok", product!!.stok.toString())

                        Spacer(Modifier.height(8.dp))

                        // Tombol Tambah
                        Button(
                            onClick = {
                                scope.launch {
                                    CartRepository.increaseItem(context, product!!)

                                    Toast.makeText(context, "Masuk Keranjang", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(PrimaryColor),
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .height(38.dp)
                        ) {
                            Text("Tambah", color = Color.White)
                        }
                    }
                }
            }

            // Deskripsi Produk
            item {
                Column {
                    Text("Deskripsi :", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE6E6E6), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        val shortDesc = product!!.deskripsi.take(180)
                        Text(
                            if (expandDescription) product!!.deskripsi else "$shortDesc...",
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            if (expandDescription) "Lihat lebih sedikit" else "Lihat Selengkapnya",
                            color = PrimaryColor,
                            modifier = Modifier.clickable {
                                expandDescription = !expandDescription
                            }
                        )
                    }
                }
            }

            // List Produk Lainnya
            if (otherProducts.isNotEmpty()) {
                item {
                    Text(
                        "Produk Lainnya",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp, max = 600.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        items(otherProducts) { item ->
                            ProductCard(
                                productRequest = item,
                                onClick = {
                                    navController.navigate("detail/${item.id_produk}") {
                                        popUpTo("detail/{id}") { inclusive = true }
                                    }
                                },
                                onAddToCart = {
                                    scope.launch {
                                        CartRepository.increaseItem(context, item)

                                        Toast.makeText(context, "Masuk Keranjang", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(26.dp)) }
        }
    }
}

@Composable
private fun DetailTextRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("$label :", fontSize = 14.sp, color = Color.DarkGray)
        Spacer(Modifier.width(6.dp))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailProductScreenPreview() {

    var search by remember { mutableStateOf("") }

    val fakeProductRequest = com.mysawah.predict.domain.model.ProductRequest(
        id_produk = 1,
        nama_produk = "Bibit Jagung Super",
        harga = 14500,
        stok = 120,
        kategori = "Bibit",
        deskripsi = "Bibit jagung unggulan dengan daya tumbuh tinggi dan hasil panen melimpah.",
        gambar_produk = "https://via.placeholder.com/300"
    )

    val fakeOtherProductRequests = listOf(
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 2,
            nama_produk = "Pupuk Organik Cair",
            harga = 18000,
            stok = 50,
            kategori = "Pupuk",
            deskripsi = "Pupuk organik cair berkualitas.",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Bibit Cabai Merah",
            harga = 22000,
            stok = 80,
            kategori = "Bibit",
            deskripsi = "Bibit cabai merah unggulan.",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Bibit Cabai Merah",
            harga = 22000,
            stok = 80,
            kategori = "Bibit",
            deskripsi = "Bibit cabai merah unggulan.",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Bibit Cabai Merah",
            harga = 22000,
            stok = 80,
            kategori = "Bibit",
            deskripsi = "Bibit cabai merah unggulan.",
            gambar_produk = "https://via.placeholder.com/300"
        )
    )

    Scaffold(containerColor = BackgroundWhite) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Spacer(Modifier.height(6.dp)) }

            // SEARCH BAR
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = {
                            Text(
                                "Cari sesuatu",
                                fontSize = 14.sp,
                                color = PrimaryColor
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = PrimaryColor
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(40.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFD9D9D9),
                            unfocusedContainerColor = Color(0xFFD9D9D9),
                            cursorColor = PrimaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(50.dp)
                            .background(PrimaryColor, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ai),
                            contentDescription = "AI",
                            tint = Color.White
                        )
                    }
                }
            }

            // DETAIL PRODUK
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEDEDED), RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .width(130.dp)
                            .height(160.dp)
                    ) {
                        AsyncImage(
                            model = fakeProductRequest.gambar_produk,
                            contentDescription = fakeProductRequest.nama_produk,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            fakeProductRequest.nama_produk,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        DetailTextRow("Kategori", fakeProductRequest.kategori)
                        DetailTextRow("Harga", "Rp ${fakeProductRequest.harga}")
                        DetailTextRow("Stok", fakeProductRequest.stok.toString())
                    }
                }
            }

            // DESKRIPSI PRODUK
            item {
                Column {
                    Text("Deskripsi :", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(fakeProductRequest.deskripsi)
                }
            }

            // RODUK LAINNYA
            item {
                Text(
                    "Produk Lainnya",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            item {
                val columns = 2
                val rows = (fakeOtherProductRequests.size + columns - 1) / columns
                val gridHeight = (rows * 260).dp

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(gridHeight),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(fakeOtherProductRequests) { item ->
                        ProductCard(productRequest = item)
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}



