package com.mysawah.predict.ui.screens.aipage1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mysawah.predict.R
import com.mysawah.predict.ui.components.ChatInputBar
import com.mysawah.predict.ui.components.ChatMessageBot
import com.mysawah.predict.ui.components.ChatMessageUser
import com.mysawah.predict.ui.theme.BackgroundWhite
import androidx.compose.ui.unit.sp
import com.mysawah.predict.ui.theme.PrimaryColor
import com.mysawah.predict.ui.theme.Typography


@Composable
fun AIPageScreen1(
    viewModel: AIPage1ViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNavigateToPage2: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {

        // TOP BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .background(Color(0xFFF1F1F1), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_ai),
                    contentDescription = "Back",
                    modifier = Modifier.size(42.dp),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "Asisten AI MySawah",
                style = Typography.titleLarge,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text("Pilih rekomendasi tanaman:", fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Tombol Halaman Aktif
            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text(
                    text = "Tanaman Utama",
                    fontSize = 15.sp
                )
            }

            // Tombol halaman tidak aktif
            OutlinedButton(
                onClick = onNavigateToPage2,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(1.dp, PrimaryColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryColor
                ),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text(
                    text = "Tanaman Pendamping",
                    fontSize = 15.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // CHAT
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(viewModel.messages) { msg ->
                if (msg.isBot) ChatMessageBot(msg.text)
                else ChatMessageUser(msg.text)
                Spacer(Modifier.height(10.dp))
            }
        }

        ChatInputBar(
            value = viewModel.input,
            onValueChange = { viewModel.input = it },
            onSend = { viewModel.sendMessage() }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAIPageScreen1() {
    AIPageScreen1()
}

