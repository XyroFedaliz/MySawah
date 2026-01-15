package com.mysawah.predict.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.mysawah.predict.R
import androidx.compose.ui.platform.LocalContext
import org.json.JSONObject
import androidx.compose.ui.window.Dialog
import com.mysawah.predict.ui.components.ErrorMessage
import com.mysawah.predict.ui.theme.*

@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccessNavigate: (String, String) -> Unit,
    onRegister: () -> Unit
) {

    val viewModel = remember { LoginViewModel() }
    val context = LocalContext.current
    val loading by viewModel.loading.collectAsState()
    val success by viewModel.success.collectAsState()
    val error by viewModel.error.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // ERROR STATE untuk Textfield
    var emailError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }

    // ERROR DIALOG
    if (error != null) {

        val cleanMessage = try {
            JSONObject(error!!)
                .optString("message", error!!)
        } catch (_: Exception) {
            error!!
        }

        LaunchedEffect(cleanMessage) {
            if (
                cleanMessage.contains("email", ignoreCase = true) ||
                cleanMessage.contains("password", ignoreCase = true) ||
                cleanMessage.contains("login", ignoreCase = true)
            ) {
                emailError = true
                passError = true
            }
        }

        Dialog(
            onDismissRequest = { }
        ) {
            ErrorMessage(
                message = cleanMessage,
                onBack = { viewModel.clearError() }
            )
        }
    }

    if (success != null) {
        onLoginSuccessNavigate(email, password)
        viewModel.resetSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {

        // Background
        Image(
            painter = painterResource(id = R.drawable.bg_welcome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(OverlayDark)
        )

        // Tombol Kembali
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier.size(40.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.size(125.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        BackgroundWhite,
                        RoundedCornerShape(topStart = 50.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Selamat Datang\nKembali",
                        fontSize = 32.sp,
                        color = PrimaryColor,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Masuk ke akun anda",
                        fontSize = 15.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // EMAIL INPUT
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        placeholder = {
                            Text("Masukkan Email", color = AccentColor)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        isError = emailError,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (emailError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (emailError) Color.Red else AccentColor,
                            cursorColor = AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // PASSWORD INPUT
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passError = false
                        },
                        placeholder = {
                            Text("Masukkan Password", color = AccentColor)
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = AccentColor
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = passError,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (passError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (passError) Color.Red else AccentColor,
                            cursorColor = AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // TOMBOL LOGIN
                    Button(
                        onClick = {
                            when {
                                email.isBlank() && password.isBlank() -> {
                                    emailError = true
                                    passError = true
                                    viewModel.setError("Email & Password wajib diisi!")
                                    return@Button
                                }
                                email.isBlank() -> {
                                    emailError = true
                                    viewModel.setError("Email tidak boleh kosong!")
                                    return@Button
                                }
                                password.isBlank() -> {
                                    passError = true
                                    viewModel.setError("Password tidak boleh kosong!")
                                    return@Button
                                }
                                password.length < 6 -> {
                                    passError = true
                                    viewModel.setError("Password minimal 6 karakter!")
                                    return@Button
                                }
                            }

                            viewModel.login(email, password, context)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Masuk", fontSize = 16.sp, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Belum punya akun?", color = DarkGrayPanel)

                        TextButton(onClick = onRegister) {
                            Text("daftar sekarang", color = SecondaryTextColor)
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Login Screen Preview"
)
@Composable
fun LoginScreenPreview() {
    MySawahTheme {
        LoginScreen(
            onBack = {},
            onLoginSuccessNavigate = { _, _ -> },
            onRegister = {}
        )
    }
}
