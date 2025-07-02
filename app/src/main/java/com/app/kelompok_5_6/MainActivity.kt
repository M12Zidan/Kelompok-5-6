package com.app.kelompok_5_6

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.kelompok_5_6.ui.theme.Kelompok56Theme

/**
 * MainActivity adalah titik masuk utama aplikasi Android.
 * Activity ini menggunakan Jetpack Compose untuk membangun antarmuka pengguna.
 */
class MainActivity : ComponentActivity() {

    /**
     * Fungsi onCreate dijalankan saat Activity dibuat untuk pertama kali.
     * Di dalamnya, kita mengaktifkan layout edge-to-edge dan mengatur konten tampilan dengan tema yang telah ditentukan.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan edge-to-edge layout agar UI dapat tampil penuh hingga ke tepi layar
        enableEdgeToEdge()

        // Menentukan konten UI dengan Jetpack Compose
        setContent {
            // Menerapkan tema aplikasi
            Kelompok56Theme {
                // Scaffold menyusun struktur dasar tampilan Material Design
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Menampilkan konten utama aplikasi, meneruskan padding sistem
                    WelcomeScreen(padding = innerPadding)
                }
            }
        }
    }
}

/**
 * WelcomeScreen adalah tampilan utama yang menampilkan pesan sambutan, input nama,
 * dan tombol untuk menampilkan toast.
 *
 * @param padding PaddingValues yang diterima dari Scaffold untuk menghindari overlap dengan sistem UI.
 */
@Composable
fun WelcomeScreen(padding: PaddingValues) {
    val context = LocalContext.current

    // State untuk menyimpan input nama pengguna
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding) // Padding dari Scaffold untuk menghindari overlap UI
            .padding(24.dp), // Padding tambahan untuk konten
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        // Menampilkan teks sambutan
        Text(
            text = "Selamat datang di Mobile Device Programming!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF512DA8),
        )

        // Input field untuk memasukkan nama pengguna
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Masukkan Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol yang akan menampilkan toast saat diklik
        Button(
            onClick = {
                Toast.makeText(context, "Halo, $name", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF512DA8),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Tampilkan Toast", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

/**
 * Fungsi Preview digunakan untuk menampilkan pratinjau UI dari WelcomeScreen di Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kelompok56Theme {
        // Menampilkan WelcomeScreen tanpa padding sistem (untuk preview saja)
        WelcomeScreen(padding = PaddingValues(0.dp))
    }
}
