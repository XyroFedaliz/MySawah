package com.mysawah.predict.ui.screens.aipage2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysawah.predict.R
import com.mysawah.predict.ui.theme.BackgroundWhite
import com.mysawah.predict.ui.theme.PrimaryColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.tooling.preview.Preview
import com.mysawah.predict.ui.theme.*

@Composable
fun AIPageScreen2(
    viewModel: AIPage2ViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit = {},
    onNavigateToPage1: () -> Unit = {}
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
                    painter = painterResource(id = R.drawable.ic_back_ai),
                    contentDescription = "Back",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(42.dp)
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
            OutlinedButton(
                onClick = onNavigateToPage1,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryColor
                ),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text(
                    text = "Tanaman Utama",
                    fontSize = 15.sp
                )
            }

            // Tombol Halaman Tidak Aktif
            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(1.dp, PrimaryColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text(
                    text = "Tanaman Pendamping",
                    fontSize = 15.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // FORM
        Text("Tanaman Utama Anda:", fontSize = 15.sp)
        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = viewModel.mainPlant,
            onValueChange = viewModel::updateMainPlant,
            enabled = viewModel.result == null,
            placeholder = { Text("Masukkan Tanaman Utama", color = AccentColor) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(Modifier.height(14.dp))

        Text("Tanaman Pendamping Anda:", fontSize = 15.sp)
        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = viewModel.companionPlant,
            onValueChange = viewModel::updateCompanionPlant,
            enabled = viewModel.result == null,
            placeholder = { Text("Masukkan Tanaman Pendamping", color = AccentColor) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(Modifier.height(14.dp))

        Text("Apa Jenis Tanaman Utama Anda:", fontSize = 15.sp)
        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = viewModel.mainPlantType,
            onValueChange = viewModel::updateMainPlantType,
            enabled = viewModel.result == null,
            placeholder = { Text("Masukkan Jenis Tanaman Utama", color = AccentColor) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.result == null) {
                    viewModel.checkResult()
                } else {
                    viewModel.reset()
                }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
        ) {
            Text(
                if (viewModel.result == null) "Cek Hasil" else "Cek Ulang", color = Color.White
            )
        }


        Spacer(Modifier.height(20.dp))

        viewModel.result?.let { text ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF1F1F1)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAICompanionPage2() {
    AIPageScreen2()
}
