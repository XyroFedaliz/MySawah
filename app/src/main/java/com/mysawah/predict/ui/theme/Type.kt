package com.mysawah.predict.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mysawah.predict.R

// ===== FONT FAMILY OUTFIT =====
val Outfit = FontFamily(
    Font(R.font.outfit_regular, FontWeight.Normal),
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_light, FontWeight.SemiBold),
    Font(R.font.outfit_bold, FontWeight.Bold)
)

// ===== TYPOGRAPHY STYLE =====
val Typography = Typography(

    // Default paragraph
    bodyLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    // Headings (judul besar)
    titleLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),

    // Button Text
    labelLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    // Helper text kecil
    bodySmall = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
