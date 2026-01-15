    package com.mysawah.predict.ui.screens.home

    import androidx.compose.foundation.background
    import androidx.compose.foundation.horizontalScroll
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
    import androidx.compose.foundation.lazy.grid.items
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavHostController
    import com.mysawah.predict.R
    import com.mysawah.predict.ui.components.ProductCard
    import androidx.navigation.compose.rememberNavController
    import com.mysawah.predict.ui.navigation.BottomNavBar
    import androidx.compose.ui.window.Dialog
    import com.mysawah.predict.data.repository.CartRepository
    import com.mysawah.predict.ui.components.FloatingCartButton
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.platform.LocalContext
    import kotlinx.coroutines.launch
    import com.mysawah.predict.ui.components.ErrorMessage
    import com.mysawah.predict.ui.theme.*

    val categories = listOf("Semua", "Bibit tanaman", "Pupuk", "Pestisida", "Vitamin", "Alat Tani")


    @Composable
    fun HomeScreen(
        navController: NavHostController,
        onAiClick: () -> Unit = {},
        previewProductRequests: List<com.mysawah.predict.domain.model.ProductRequest>? = null
    ) {

        val viewModel = remember { HomeViewModel() }
        val scope = rememberCoroutineScope()
        val cartItems by CartRepository.cartItems.collectAsState()
        val productsFromApi = previewProductRequests ?: viewModel.products.collectAsState().value
        val loading by viewModel.loading.collectAsState()
        val context = LocalContext.current
        val error by viewModel.error.collectAsState()
        var search by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf(categories.first()) }

        LaunchedEffect(Unit) {
            viewModel.getProducts()
        }

        LaunchedEffect(Unit) {
            CartRepository.fetchCartFromServer(context)
        }

        val filteredProducts = productsFromApi.filter { product ->
            val matchCategory =
                selectedCategory == "Semua" ||
                        product.kategori.equals(selectedCategory, ignoreCase = true)

            val matchSearch =
                product.nama_produk.contains(search, ignoreCase = true)

            matchCategory && matchSearch
        }

        Scaffold(
            bottomBar = { BottomNavBar(navController, selectedIndex = 0) },
            containerColor = BackgroundWhite,
            floatingActionButton = {
                FloatingCartButton(
                    visible = cartItems.isNotEmpty(),
                    onClick = { navController.navigate("cart") }
                )
            }
        ) { padding ->

            // ERROR DIALOG
            if (error != null) {
                Dialog(onDismissRequest = { viewModel.clearError() }) {
                    ErrorMessage(
                        message = error ?: "",
                        onBack = { viewModel.clearError() }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(Modifier.height(12.dp))

                // TOP BAR
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
                                style = Typography.bodySmall,
                                fontSize = 15.sp,
                                color = PrimaryColor
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryColor)
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
                        onClick = { onAiClick() },
                        modifier = Modifier
                            .size(50.dp)
                            .background(PrimaryColor, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ai),
                            contentDescription = "AI",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // CATEGORY FILTER
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.forEach { category ->
                        AssistChip(
                            onClick = { selectedCategory = category },
                            label = { Text(category, style = Typography.labelLarge) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (selectedCategory == category) PrimaryColor else Color.White,
                                labelColor = if (selectedCategory == category) Color.White else DarkGrayPanel
                            ),
                            shape = RoundedCornerShape(50)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                when {
                    loading && productsFromApi.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = PrimaryColor)
                        }
                    }

                    productsFromApi.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Produk tidak ada", color = Color.Gray)
                        }
                    }

                    filteredProducts.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Tidak ada produk ditemukan", color = Color.Gray)
                        }
                    }

                    // Hasil Filter
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(filteredProducts) { product ->
                                ProductCard(
                                    productRequest = product,
                                    onAddToCart = {
                                        scope.launch {
                                            CartRepository.increaseItem(context, product)

                                        }},
                                    onClick = {
                                        navController.navigate("detail/${product.id_produk}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private val dummyProductRequests = listOf(
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 1,
            nama_produk = "Bibit Jagung Super",
            harga = 14500,
            stok = 120,
            kategori = "Bibit tanaman",
            deskripsi = "Bibit jagung unggulan",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 2,
            nama_produk = "Pupuk Organik Cair",
            harga = 18000,
            stok = 75,
            kategori = "Pupuk",
            deskripsi = "Pupuk organik cair",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Pestisida Nabati",
            harga = 25000,
            stok = 40,
            kategori = "Pestisida",
            deskripsi = "Pestisida alami",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Pestisida Nabati",
            harga = 25000,
            stok = 40,
            kategori = "Pestisida",
            deskripsi = "Pestisida alami",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Pestisida Nabati",
            harga = 25000,
            stok = 40,
            kategori = "Pestisida",
            deskripsi = "Pestisida alami",
            gambar_produk = "https://via.placeholder.com/300"
        ),
        com.mysawah.predict.domain.model.ProductRequest(
            id_produk = 3,
            nama_produk = "Pestisida Nabati",
            harga = 25000,
            stok = 40,
            kategori = "Pestisida",
            deskripsi = "Pestisida alami",
            gambar_produk = "https://via.placeholder.com/300"
        )
    )

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun HomeScreenPreview() {
        MySawahTheme {
            HomeScreen(
                navController = rememberNavController(),
                previewProductRequests = dummyProductRequests
            )
        }
    }


