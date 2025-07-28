package com.app.kelompok_5_6.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.kelompok_5_6.screen.HomeScreen
import com.app.kelompok_5_6.screen.LoginScreen
import com.app.kelompok_5_6.screen.ProfileScreen
import com.app.kelompok_5_6.screen.RegisterScreen

/**
 * Fungsi `SetupNavGraph` digunakan untuk mengatur struktur navigasi di aplikasi.
 * Di dalamnya didefinisikan semua halaman yang dapat dinavigasi beserta argumen yang dibutuhkan (jika ada).
 *
 * @param navController controller yang digunakan untuk melakukan navigasi antar halaman.
 * @param modifier modifier opsional untuk styling tambahan pada `NavHost`.
 */
@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route, // Halaman pertama saat aplikasi dijalankan
        modifier = modifier
    ) {
        // Rute ke halaman Home
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }

        // Rute ke halaman Result dengan parameter "text"


        // Rute ke halaman Profile
        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        // Rute ke halaman Login
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }

        // Rute ke halaman Register
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
    }
}