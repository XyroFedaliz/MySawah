package com.mysawah.predict.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mysawah.predict.R
import com.mysawah.predict.ui.theme.MySawahTheme
import com.mysawah.predict.ui.theme.PrimaryColor

@Composable
fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .navigationBarsPadding()
                .height(55.dp),
            placeholder = {
                Text(
                    "Ketik pesan...",
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(30.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),

            // WARNA OUTLINED
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = PrimaryColor
            ),

            trailingIcon = {
                IconButton(
                    onClick = onSend,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Send",
                        tint = PrimaryColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatInputBarPreview() {
    MySawahTheme {
        var text by remember { mutableStateOf("") }

        ChatInputBar(
            value = text,
            onValueChange = { text = it },
            onSend = {}
        )
    }
}
