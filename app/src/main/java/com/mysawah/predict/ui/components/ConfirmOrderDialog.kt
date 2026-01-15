package com.mysawah.predict.ui.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ConfirmOrderDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Apakah Anda yakin ingin melakukan\n pemesanan?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Tombol Batal
                OutlinedButton(
                    onClick = onCancel,
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Batal", fontSize = 16.sp)
                }

                // Tombol Konfirmasi
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A9250),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Yakin", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmOrderDialog() {
    ConfirmOrderDialog(
        onCancel = {},
        onConfirm = {}
    )
}
