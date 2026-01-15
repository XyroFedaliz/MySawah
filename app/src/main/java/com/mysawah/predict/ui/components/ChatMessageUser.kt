package com.mysawah.predict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMessageUser(
    text: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.End
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = Color(0x26000000),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .padding(14.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessageUserPreview() {
    ChatMessageUser(
        text = "Ini adalah pesan dari pengguna yang tampil di sebelah kanan."
    )
}