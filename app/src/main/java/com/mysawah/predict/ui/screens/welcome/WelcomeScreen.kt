package com.mysawah.predict.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysawah.predict.R
import com.mysawah.predict.ui.theme.MySawahTheme

@Composable
fun WelcomeScreen(
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_welcome),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "Aplikasi\nTerbaik untuk\nLahan Anda",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 52.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(170.dp))

            // Tombol Daftar
            Button(
                onClick = onRegister,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFB3BE68)
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tombol Masuk
            Button(
                onClick = onLogin,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB3BE68)
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Masuk",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2340px,dpi=440"
)
@Composable
fun WelcomeScreenPreview() {
    MySawahTheme {
        WelcomeScreen()
    }
}
