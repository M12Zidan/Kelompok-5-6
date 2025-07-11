package com.app.kelompok_5_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.app.kelompok_5_6.ui.theme.Kelompok56Theme

/**
 * MainActivity adalah titik masuk utama aplikasi Android ini.
 * Menggunakan Jetpack Compose untuk mendefinisikan UI aplikasi.
 */
class MainActivity : ComponentActivity() {

    /**
     * Fungsi onCreate dijalankan saat activity pertama kali dibuat.
     * Di sini kita mengaktifkan edge-to-edge layout dan menampilkan UI dengan tema Compose.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan tampilan penuh sampai ke edge (tanpa padding sistem)
        enableEdgeToEdge()

        // Menentukan konten UI menggunakan Compose
        setContent {
            // Menerapkan tema aplikasi
            Kelompok56Theme {
                // Membuat controller navigasi untuk mengatur halaman
                val navController = rememberNavController()

                // Menampilkan UI utama aplikasi
                MyApp(navController)
            }
        }
    }
}

/**
 * Fungsi ini digunakan untuk menampilkan preview dari UI utama (MyApp) di Android Studio.
 * Preview ini membantu saat desain UI menggunakan Compose.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kelompok56Theme {
        // Membuat navController sementara untuk preview
        val navController = rememberNavController()

        // Menampilkan UI utama di preview
        MyApp(navController)
    }
}