package com.mysawah.predict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.mysawah.predict.R

@Composable
fun ChatMessageBot(
    text: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.Top
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_profile_bot),
            contentDescription = "Bot",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(56.dp)
                .padding(end = 10.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color(0xFFD9DEB3),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .padding(14.dp)
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
fun ChatMessageBotPreview() {
    ChatMessageBot(
        text = "Halo 👋 Saya adalah AI yang siap membantu prediksi sawah Anda."
    )
}