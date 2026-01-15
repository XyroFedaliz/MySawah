package com.mysawah.predict.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mysawah.predict.R
import com.mysawah.predict.domain.model.ProductRequest
import com.mysawah.predict.ui.theme.MySawahTheme
import com.mysawah.predict.ui.theme.Typography

@Composable
fun ProductCard(
    productRequest: ProductRequest,
    onClick: (ProductRequest) -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(165.dp)
            .height(246.dp)
            .clickable { onClick(productRequest) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDCDCDC)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gambar Produk
            AsyncImage(
                model = productRequest.gambar_produk,
                contentDescription = productRequest.nama_produk,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Nama Produk
                Text(
                    text = productRequest.nama_produk,
                    fontSize = 14.sp,
                    style = Typography.bodyLarge,
                    color = Color.Black,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Harga & Tombol Tambah
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rp ${productRequest.harga}",
                        fontSize = 16.sp,
                        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )

                    // Tombol Plus kecil
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "Tambah",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    MySawahTheme {
        ProductCard(
            productRequest = ProductRequest(
                id_produk = 1,
                nama_produk = "Bibit Semangka",
                harga = 25000,
                stok = 100,
                kategori = "Bibit Tanaman",
                deskripsi = "Bibit unggul semangka",
                gambar_produk = "https://via.placeholder.com/150"
            ),
            onClick = {},
            onAddToCart = {}
        )
    }
}