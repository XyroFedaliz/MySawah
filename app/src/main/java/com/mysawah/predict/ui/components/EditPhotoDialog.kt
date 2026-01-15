    package com.mysawah.predict.ui.components
    
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.compose.ui.window.Dialog
    import com.mysawah.predict.ui.theme.DarkGrayPanel
    import com.mysawah.predict.ui.theme.MySawahTheme
    import com.mysawah.predict.ui.theme.PrimaryColor


    @Composable
    fun EditPhotoDialog(
        onDismiss: () -> Unit = {},
        onGallery: () -> Unit = {},
        onCamera: () -> Unit = {},
        onDelete: () -> Unit = {}
    ) {
        Dialog(onDismissRequest = onDismiss) {
    
            Card(
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
    
                Column {
    
                    DialogItemBox(
                        text = "Galeri",
                        onClick = {
                            onGallery()
                            onDismiss()
                        }
                    )
    
                    Separator()
    
                    DialogItemBox(
                        text = "Ambil Foto",
                        onClick = {
                            onCamera()
                            onDismiss()
                        }
                    )
    
                    Separator()
    
                    DialogItemBox(
                        text = "Hapus Foto",
                        textColor = PrimaryColor,
                        onClick = {
                            onDelete()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun DialogItemBox(
        text: String,
        textColor: Color = Color.Black,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 16.dp, horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                color = textColor,
                textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    fun Separator() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DarkGrayPanel)
        )
    }
    

    @Preview(showBackground = true)
    @Composable
    fun EditPhotoDialogPreview() {
        MySawahTheme {
            EditPhotoDialog()
        }
    }
