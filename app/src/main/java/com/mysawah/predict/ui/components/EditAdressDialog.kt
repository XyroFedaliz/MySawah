package com.mysawah.predict.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip


@Composable
fun EditAdressDialog(
    currentName: String = "",
    onCancel: () -> Unit,
    onSave: (String) -> Unit
) {
    var newName by remember { mutableStateOf(currentName) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Masukkan Alamat Anda",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = {
                        Text("Masukkan Alamat Anda")
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF818C3C),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF818C3C),
                        unfocusedBorderColor = Color(0xFF818C3C),
                        focusedContainerColor = Color(0xFFF2F5F3),
                        unfocusedContainerColor = Color(0xFFF2F5F3)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Tombol Batal
                    OutlinedButton(
                        onClick = onCancel,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Batal", color = Color.Black, fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Tombol Simpan
                    Button(
                        onClick = { onSave(newName) },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF818C3C) // hijau olive
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Simpan", color = Color.White, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewEditAdressDialog() {
    EditAdressDialog(
        currentName = "",
        onCancel = {},
        onSave = {}
    )
}
