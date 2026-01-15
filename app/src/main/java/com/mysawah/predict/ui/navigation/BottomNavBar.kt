package com.mysawah.predict.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mysawah.predict.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mysawah.predict.ui.screens.home.HomeScreen
import com.mysawah.predict.ui.theme.MySawahTheme
import com.mysawah.predict.ui.theme.PrimaryColor
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.mysawah.predict.ui.theme.Typography

@Composable
fun BottomNavBar(
    navController: NavHostController,
    selectedIndex: Int = 0
) {
    val unselectedColor = Color(0xFF646A3C)

    NavigationBar(containerColor = PrimaryColor) {

        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { navController.navigate("home") },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_home),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Beranda", style = Typography.bodyMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { navController.navigate("cart") },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_pesan),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Keranjang", style = Typography.bodyMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { navController.navigate("orders") },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_order),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Pesanan", style = Typography.bodyMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = { navController.navigate("profile") },
            icon = {
                Icon(
                    painterResource(R.drawable.ic_acc),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Akun", style = Typography.bodyMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MySawahTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}

