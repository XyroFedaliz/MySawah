package com.mysawah.predict.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysawah.predict.R
import androidx.compose.ui.window.Dialog
import com.mysawah.predict.ui.components.ErrorMessage
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.mysawah.predict.ui.theme.*

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onSuccessRegister: () -> Unit,
    onLoginNavigate: () -> Unit
) {
    val viewModel = remember { RegisterViewModel() }
    val context = LocalContext.current
    val loading by viewModel.loading.collectAsState()
    val success by viewModel.success.collectAsState()
    val error by viewModel.error.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // STATE ERROR FIELD
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    // ERROR DIALOG
    if (error != null) {
        Dialog(onDismissRequest = {}) {
            ErrorMessage(
                message = error ?: "",
                onBack = { viewModel.clearError() }
            )
        }
    }

    // SUCCESS DIALOG
    if (success != null) {
        Dialog(onDismissRequest = {}) {
            ErrorMessage(
                message = success ?: "",
                onBack = {
                    viewModel.clearSuccess()
                    onSuccessRegister()
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_welcome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OverlayDark)
        )

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

            Spacer(modifier = Modifier.height(180.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        BackgroundWhite,
                        RoundedCornerShape(topStart = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Selamat datang", fontSize = 38.sp, color = PrimaryColor)
                    Text(
                        "Silahkan buat akun baru anda",
                        fontSize = 15.sp,
                        color = DarkGrayPanel
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // USERNAME INPUT
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            usernameError = false
                        },
                        placeholder = { Text("Masukkan Username", color = AccentColor) },
                        singleLine = true,
                        isError = usernameError,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (usernameError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (usernameError) Color.Red else AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // EMAIL INPUT
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        placeholder = { Text("Masukkan Email", color = AccentColor) },
                        singleLine = true,
                        isError = emailError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (emailError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (emailError) Color.Red else AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // PASSWORD INPUT
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = false
                        },
                        placeholder = { Text("Masukkan Password", color = AccentColor) },
                        singleLine = true,
                        isError = passwordError,
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = AccentColor
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (passwordError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (passwordError) Color.Red else AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // CONFIRM PASSWORD INPUT
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = false
                        },
                        placeholder = { Text("Konfirmasi Password", color = AccentColor) },
                        singleLine = true,
                        isError = confirmPasswordError,
                        visualTransformation = if (confirmVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmVisible = !confirmVisible }) {
                                Icon(
                                    if (confirmVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = AccentColor
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (confirmPasswordError) Color.Red else AccentColor,
                            unfocusedBorderColor = if (confirmPasswordError) Color.Red else AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // TOMBOL REGISTER
                    Button(
                        onClick = {
                            usernameError = false
                            emailError = false
                            passwordError = false
                            confirmPasswordError = false

                            when {
                                username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                    usernameError = username.isBlank()
                                    emailError = email.isBlank()
                                    passwordError = password.isBlank()
                                    confirmPasswordError = confirmPassword.isBlank()

                                    viewModel.setError("Semua kolom harus diisi!")
                                    return@Button
                                }

                                password.length < 6 -> {
                                    passwordError = true
                                    viewModel.setError("Password harus minimal 6 karakter!")
                                    return@Button
                                }

                                password != confirmPassword -> {
                                    confirmPasswordError = true
                                    viewModel.setError("Password tidak sama!")
                                    return@Button
                                }
                            }

                            viewModel.register(username, email, password, context)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text("Daftar", fontSize = 16.sp, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sudah punya akun?", color = Color.Black)
                        TextButton(onClick = onLoginNavigate) {
                            Text("masuk sekarang.", color = SecondaryTextColor)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegisterOnly() {
    MySawahTheme {
        RegisterScreen(
            onBack = {},
            onSuccessRegister = {},
            onLoginNavigate = {}
        )
    }
}

