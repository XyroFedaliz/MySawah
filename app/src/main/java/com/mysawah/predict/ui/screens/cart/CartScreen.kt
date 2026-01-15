package com.mysawah.predict.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.mysawah.predict.R
import com.mysawah.predict.ui.navigation.BottomNavBar
import androidx.navigation.compose.rememberNavController
import com.mysawah.predict.ui.theme.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mysawah.predict.data.repository.CartRepository
import com.mysawah.predict.ui.components.ConfirmOrderDialog
import com.mysawah.predict.ui.components.CancelOrderDialog // <--- Pastikan import ini
import java.text.NumberFormat
import androidx.compose.runtime.rememberCoroutineScope
import com.mysawah.predict.domain.model.CartItemDetail
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun CartScreen(
    navController: NavHostController,
    onAiClick: () -> Unit = {},
    selectedIndex: Int = 1
) {
    val viewModel: CartViewModel = viewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // State Data
    val cartItems by CartRepository.cartItems.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val totalPrice = cartItems.sumOf { it.product.harga * it.qty }

    // State Dialog
    var showPayDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        CartRepository.fetchCartFromServer(context)
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController, selectedIndex) },
        containerColor = BackgroundWhite
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(12.dp))

                // HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(50.dp))
                    Text("Keranjang Anda",
                        style = Typography.titleLarge,
                        fontSize = 22.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center)

                    IconButton(
                        onClick = { onAiClick() },
                        modifier = Modifier
                            .size(42.dp)
                            .background(PrimaryColor, shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ai),
                            contentDescription = "AI",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))

                // TOTAL BOX
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(painterResource(id = R.drawable.ic_cart), contentDescription = null, tint = PrimaryColor, modifier = Modifier.size(80.dp))

                        Column(horizontalAlignment = Alignment.End) {
                            Text("Total :", style = Typography.titleLarge, fontSize = 20.sp, color = DarkGrayPanel)
                            Text(
                                text = formatRupiah(totalPrice),
                                style = Typography.titleLarge.copy(fontSize = 24.sp),
                                color = PrimaryColor
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // LIST ITEM
                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                        Text("Keranjang masih kosong", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(cartItems) { item ->
                            CartItemRow(item, context, scope)
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Tombol BATALKAN
                    OutlinedButton(
                        onClick = {
                            showCancelDialog = true
                        },
                        shape = RoundedCornerShape(20.dp),
                        enabled = cartItems.isNotEmpty() && !loading
                    ) {
                        Text("Batalkan", fontSize = 15.sp, color = DarkGrayPanel)
                    }

                    Spacer(Modifier.width(12.dp))

                    // Tombol BAYAR
                    Button(
                        onClick = {
                            showPayDialog = true
                        },
                        enabled = cartItems.isNotEmpty() && !loading,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        } else {
                            Text("Bayar", fontSize = 15.sp, color = Color.White)
                        }
                    }
                }
            }


            // DIALOG KONFIRMASI BAYAR
            if (showPayDialog) {
                Dialog(onDismissRequest = { showPayDialog = false }) {
                    ConfirmOrderDialog(
                        onCancel = { showPayDialog = false },
                        onConfirm = {
                            showPayDialog = false
                            viewModel.payOrder(context) {
                                navController.navigate("orders")
                            }
                        }
                    )
                }
            }

            // DIALOG KONFIRMASI BATAL
            if (showCancelDialog) {
                Dialog(onDismissRequest = { showCancelDialog = false }) {
                    CancelOrderDialog(
                        onCancel = { showCancelDialog = false },
                        onConfirm = {
                            showCancelDialog = false
                            viewModel.cancelOrder(context)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItemDetail,
    context: android.content.Context,
    scope: kotlinx.coroutines.CoroutineScope
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(item.product.nama_produk, style = Typography.titleLarge, fontSize = 18.sp)

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    scope.launch { CartRepository.decreaseItem(context, item.product) }
                }) {
                    Icon(painterResource(R.drawable.ic_min), contentDescription = "-", tint = Color.Black, modifier = Modifier.size(22.dp))
                }

                Text(item.qty.toString(), style = Typography.bodyLarge, fontSize = 18.sp)

                IconButton(onClick = {
                    scope.launch { CartRepository.increaseItem(context, item.product) }
                }) {
                    Icon(painterResource(R.drawable.ic_pluss), contentDescription = "+", tint = Color.Black, modifier = Modifier.size(22.dp))
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Rp ${item.product.harga}", style = Typography.bodySmall, fontSize = 14.sp)
            Text(item.product.kategori, style = Typography.bodySmall, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(Modifier.height(18.dp))
    }
}

fun formatRupiah(number: Int): String {
    val localeID = Locale.forLanguageTag("id-ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number).replace("Rp", "Rp ")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    MySawahTheme {
        CartScreen(
            navController = rememberNavController(),
            onAiClick = {},
            selectedIndex = 1
        )
    }
}


