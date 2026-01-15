package com.mysawah.predict.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.mysawah.predict.ui.theme.MySawahTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysawah.predict.R

@Composable
fun EmptyCartComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(20.dp))
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Empty Cart",
                tint = Color(0xFF6E7541),
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Maaf, anda belum ada pesanan",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEmptyCart() {
    MySawahTheme {
        EmptyCartComponent()
    }
}
