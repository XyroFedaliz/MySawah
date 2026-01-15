package com.mysawah.predict.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mysawah.predict.data.local.SaveToken
import com.mysawah.predict.ui.screens.account.AccountScreen
import com.mysawah.predict.ui.screens.welcome.WelcomeScreen
import com.mysawah.predict.ui.screens.login.LoginScreen
import com.mysawah.predict.ui.screens.register.RegisterScreen
import com.mysawah.predict.ui.screens.home.HomeScreen
import com.mysawah.predict.ui.screens.detailproduct.DetailProductScreen
import com.mysawah.predict.ui.screens.cart.CartScreen
import com.mysawah.predict.ui.screens.order.PesananScreen
import com.mysawah.predict.ui.screens.aipage1.AIPageScreen1
import com.mysawah.predict.ui.screens.aipage2.AIPageScreen2

@Composable
fun AppNavigation(
    startDestination: String
) {

    val navController: NavHostController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // --- WELCOME ---
        composable("welcome") {
            WelcomeScreen(
                onLogin = { navController.navigate("login") },
                onRegister = { navController.navigate("register") }
            )
        }

        // --- LOGIN ---
        composable("login") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccessNavigate = { _, _ ->
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onRegister = { navController.navigate("register") }
            )
        }

        // --- REGISTER ---
        composable("register") {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onSuccessRegister = {
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onLoginNavigate = { navController.navigate("login") }
            )
        }

        // --- HOME ---
        composable("home") {
            HomeScreen(
                navController = navController,
                onAiClick = { navController.navigate("aipage1") }
            )
        }

        // --- DETAIL PRODUCT ---
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            DetailProductScreen(
                productId = id,
                navController = navController,
                onAiClick = { navController.navigate("aipage1") }
            )
        }

        // --- CART ---
        composable("cart") { CartScreen(navController)
            CartScreen(
                navController = navController,
                onAiClick = { navController.navigate("aipage1") },
            )

        }

        // --- ORDER ---
        composable("orders") { PesananScreen(navController)
            PesananScreen(
                navController = navController,
                onNavigateAi = { navController.navigate("aipage1") },
            )
        }

        // --- PROFILE ---
        composable("profile") {
            AccountScreen(
                navController = navController,
                onLogout = {
                    SaveToken.clearToken(context)

                    navController.navigate("welcome") {
                        popUpTo(0)
                    }
                },
                onNavigateAi = { navController.navigate("aipage1") }
            )
        }

        // --- AI PAGES ---
        composable("aipage1") {
            AIPageScreen1(
                onBack = { navController.popBackStack() },
                onNavigateToPage2 = { navController.navigate("aipage2") }
            )
        }

        composable("aipage2") {
            AIPageScreen2(
                onBack = { navController.navigate("home") },
                onNavigateToPage1 = { navController.popBackStack() }
            )
        }
    }
}