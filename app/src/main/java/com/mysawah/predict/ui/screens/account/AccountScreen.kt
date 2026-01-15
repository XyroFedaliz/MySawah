package com.mysawah.predict.ui.screens.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mysawah.predict.R
import com.mysawah.predict.data.local.SaveToken
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.UserRequest
import com.mysawah.predict.ui.components.*
import com.mysawah.predict.ui.navigation.BottomNavBar
import com.mysawah.predict.ui.theme.MySawahTheme
import com.mysawah.predict.ui.theme.PrimaryColor
import com.mysawah.predict.ui.theme.Typography
import androidx.core.content.FileProvider
import java.io.File


@Composable
fun AccountScreen(
    navController: NavHostController,
    selectedIndex: Int = 3,
    onNavigateAi: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val viewModel: AccountViewModel = viewModel()
    val context = LocalContext.current

    val userState by viewModel.userData.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val token = remember { SaveToken.getToken(context) }
    val imageSignature by viewModel.imageSignature.collectAsState()
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // State Dialog
    val showName by viewModel.showNameDialog.collectAsState()
    val showPhone by viewModel.showPhoneDialog.collectAsState()
    val showAddress by viewModel.showAddressDialog.collectAsState()
    val showPhoto by viewModel.showPhotoDialog.collectAsState()

    // LAUNCHER UNTUK GALERI
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadPhoto(context, it) }
    }

    // 3. LAUNCHER KAMERA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) {
            // Jika foto berhasil diambil, upload URI sementara tadi
            viewModel.uploadPhoto(context, tempPhotoUri!!)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile(context)
    }

    if (showName) {
        Dialog(onDismissRequest = { viewModel.closeNameDialog() }) {
            EditNameDialog(
                currentName = userState?.name ?: "",
                onCancel = { viewModel.closeNameDialog() },
                onSave = { viewModel.updateProfile(context, newName = it) }
            )
        }
    }

    if (showPhone) {
        Dialog(onDismissRequest = { viewModel.closePhoneDialog() }) {
            EditNumberDialog(
                currentName = userState?.no_hp ?: "",
                onCancel = { viewModel.closePhoneDialog() },
                onSave = { viewModel.updateProfile(context, newPhone = it) }
            )
        }
    }

    if (showAddress) {
        Dialog(onDismissRequest = { viewModel.closeAddressDialog() }) {
            EditAdressDialog(
                currentName = userState?.alamat ?: "",
                onCancel = { viewModel.closeAddressDialog() },
                onSave = { viewModel.updateProfile(context, newAddress = it) }
            )
        }
    }

    if (showPhoto) {
        EditPhotoDialog(
            onDismiss = { viewModel.closePhotoDialog() },
            onGallery = { galleryLauncher.launch("image/*") },
            onCamera = {
                val photoFile = File.createTempFile(
                    "IMG_${System.currentTimeMillis()}",
                    ".jpg",
                    context.cacheDir
                )
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider", // Authority
                    photoFile
                )
                tempPhotoUri = uri
                cameraLauncher.launch(uri)
            },
            onDelete = { viewModel.deletePhoto(context) }
        )
    }

    AccountContent(
        navController = navController,
        selectedIndex = selectedIndex,
        userState = userState,
        loading = loading,
        token = token,
        imageSignature = imageSignature,
        onNavigateAi = onNavigateAi,
        onLogout = onLogout,
        onEditNameClick = { viewModel.openNameDialog() },
        onEditPhotoClick = { viewModel.openPhotoDialog() },
        onEditPhoneClick = { viewModel.openPhoneDialog() },
        onEditAddressClick = { viewModel.openAddressDialog() }
    )
}


@Composable
fun AccountContent(
    navController: NavHostController,
    selectedIndex: Int,
    userState: UserRequest?,
    loading: Boolean,
    token: String?,
    imageSignature: Long,
    onNavigateAi: () -> Unit,
    onLogout: () -> Unit,
    onEditNameClick: () -> Unit,
    onEditPhotoClick: () -> Unit,
    onEditPhoneClick: () -> Unit,
    onEditAddressClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, selectedIndex = selectedIndex) }
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize()
                    .background(Color(0xFF4D512F))
            ) {

                // HEADER
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Box
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                        Text(
                            text = "Akun Anda",
                            style = Typography.titleLarge,
                            fontSize = 22.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        IconButton(
                            onClick = onNavigateAi,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(42.dp)
                                .background(PrimaryColor, RoundedCornerShape(50))
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_ai),
                                contentDescription = "AI",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    // Profile Section
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 45.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(105.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFFB3BE68)),
                            contentAlignment = Alignment.Center
                        ) {
                            val imageUrl = "${ApiConfig.LARAVELCUY}user/photo?t=$imageSignature"
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(imageUrl) // URL sudah berubah, Coil terpaksa download baru
                                    .addHeader("Authorization", "Bearer $token")
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .memoryCachePolicy(CachePolicy.DISABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(115.dp),
                                error = painterResource(id = R.drawable.ic_profile_sample),
                                placeholder = painterResource(id = R.drawable.ic_profile_sample)
                            )
                        }

                        Spacer(Modifier.width(18.dp))

                        Column {
                            Text(
                                text = userState?.name ?: "Memuat...",
                                color = Color.White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = userState?.email ?: "",
                                color = Color.LightGray,
                                fontSize = 18.sp
                            )
                            Spacer(Modifier.height(10.dp))

                            ProfileActionRow(
                                icon = R.drawable.ic_edit_nama,
                                text = "edit nama",
                                onClick = onEditNameClick
                            )
                            Spacer(Modifier.height(6.dp))

                            ProfileActionRow(
                                icon = R.drawable.ic_edit_foto,
                                text = "edit foto",
                                onClick = onEditPhotoClick
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .offset(y = 20.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    FormSection(
                        label = "No. Handphone",
                        boxHeight = 50.dp,
                        value = userState?.no_hp,
                        onClick = onEditPhoneClick
                    )
                    Spacer(Modifier.height(22.dp))
                    FormSection(
                        label = "Alamat",
                        boxHeight = 110.dp,
                        value = userState?.alamat,
                        onClick = onEditAddressClick
                    )
                    Spacer(Modifier.height(40.dp))
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8D9654),
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Keluar", fontSize = 17.sp, modifier = Modifier.padding(end = 12.dp))
                            Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = null, modifier = Modifier.size(22.dp))
                        }
                    }
                }
            }

            // LOADING INDICATOR
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}


@Composable
fun FormSection(
    label: String,
    boxHeight: Dp,
    value: String?,
    onClick: () -> Unit
) {
    Text("$label :", style = Typography.titleLarge, fontSize = 17.sp, color = Color.Black)
    Spacer(Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = if (value.isNullOrEmpty()) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        if (value.isNullOrEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tambah", fontSize = 15.sp, color = Color.Black)
                Spacer(Modifier.width(6.dp))
                Icon(
                    painterResource(id = R.drawable.ic_plus),
                    contentDescription = "+",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else {
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileActionRow(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(text, color = Color.White, fontSize = 13.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreenPreview() {
    val dummyUser = UserRequest(
        id = 1,
        name = "Petani Sukses",
        email = "petani@mysawah.com",
        no_hp = "081234567890",
        alamat = "Jl. Pertanian No. 1, Desa Makmur",
        foto_profil = null
    )

    MySawahTheme {
        AccountContent(
            navController = rememberNavController(),
            selectedIndex = 3,
            userState = dummyUser,
            loading = false,
            token = "dummy_token",
            imageSignature = 0L,
            onNavigateAi = {},
            onLogout = {},
            onEditNameClick = {},
            onEditPhotoClick = {},
            onEditPhoneClick = {},
            onEditAddressClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun AccountScreenLoadingPreview() {
    MySawahTheme {
        AccountContent(
            navController = rememberNavController(),
            selectedIndex = 3,
            userState = null,
            loading = true,
            token = null,
            imageSignature = 0L,
            onNavigateAi = {},
            onLogout = {},
            onEditNameClick = {},
            onEditPhotoClick = {},
            onEditPhoneClick = {},
            onEditAddressClick = {}
        )
    }
}