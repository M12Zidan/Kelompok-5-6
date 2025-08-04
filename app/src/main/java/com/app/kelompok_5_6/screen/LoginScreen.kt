package com.app.kelompok_5_6.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.kelompok_5_6.model.request.LoginRequest
import com.app.kelompok_5_6.navigation.Screen
import com.app.kelompok_5_6.service.api.ApiClient
import com.app.kelompok_5_6.utils.PreferenceManager
import kotlinx.coroutines.launch

/**
 * Composable `LoginScreen` digunakan untuk menampilkan form login.
 * Fungsi ini memungkinkan pengguna untuk memasukkan username dan password
 * lalu memverifikasi kredensial melalui API.
 *
 * @param navController controller navigasi untuk berpindah ke halaman lain setelah login.
 */
@Composable
fun LoginScreen(navController: NavHostController) {
    // State untuk menyimpan input username dan password
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State untuk menanda  i apakah terdapat error pada input
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading saat proses login berlangsung
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas untuk manajemen UI
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Tata letak kolom vertikal, berada di tengah layar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(), // Memberi ruang tambahan ketika keyboard muncul
        verticalArrangement = Arrangement.Center
    ) {
        // Judul halaman login
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Input field untuk username
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = false // Reset error ketika user mengetik ulang
            },
            isError = usernameError,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tampilkan pesan error jika username kosong
        if (usernameError) {
            Text(
                "Username tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field untuk password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            isError = passwordError,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Sembunyikan karakter
            modifier = Modifier.fillMaxWidth()
        )

        // Tampilkan pesan error jika password kosong
        if (passwordError) {
            Text(
                "Password tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol login untuk mengirim permintaan ke server
        Button(
            onClick = {
                focusManager.clearFocus() // Tutup keyboard
                usernameError = username.isBlank()
                passwordError = password.isBlank()

                // Jika input valid, kirim permintaan login
                if (!usernameError && !passwordError) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val response = ApiClient.instance.login(
                                LoginRequest(username = username, password = password)
                            )
                            isLoading = false
                            val body = response.body()

                            if (response.isSuccessful && body?.code == 200) {
                                val token = body.token
                                PreferenceManager.setToken(context,token.toString())
                                // Login sukses, navigasi ke halaman Home
                                Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            } else {
                                // Login gagal, tampilkan pesan dari server
                                val errorMessage = body?.message ?: response.message()
                                Toast.makeText(context, "Gagal: $errorMessage", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            // Tangani kesalahan (contoh: masalah jaringan)
                            isLoading = false
                            Toast.makeText(context, "Terjadi kesalahan: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navigasi ke halaman register jika belum punya akun
        TextButton(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Belum punya akun? Daftar")
        }
    }

    // Menampilkan dialog loading saat login sedang diproses
    if (isLoading) {
        AlertDialog(
            onDismissRequest = {}, // Tidak bisa ditutup manual
            confirmButton = {}, // Tidak ada tombol aksi
            title = { Text("Loading") },
            text = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Sedang login...")
                }
            }
        )
    }
}
