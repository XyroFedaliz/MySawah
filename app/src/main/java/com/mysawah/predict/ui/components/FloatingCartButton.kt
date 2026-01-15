package com.mysawah.predict.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
fun FloatingCartButton(
    visible: Boolean = true,
    onClick: () -> Unit,

) {
    if (!visible) return

    Surface(
        modifier = Modifier
            .size(64.dp)
            .clickable { onClick() },
        shape = CircleShape,
        color = PrimaryColor,
        shadowElevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Cart",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingCartButtonPreview() {
    MySawahTheme {
        FloatingCartButton(
            visible = true,
            onClick = {}
        )
    }
}
