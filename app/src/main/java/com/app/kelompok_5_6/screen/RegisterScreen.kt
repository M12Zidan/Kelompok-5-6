package com.app.kelompok_5_6.screen

import android.util.Patterns
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
import com.app.kelompok_5_6.model.request.RegisterRequest
import com.app.kelompok_5_6.navigation.Screen
import com.app.kelompok_5_6.service.api.ApiClient
import kotlinx.coroutines.launch

/**
 * Composable `RegisterScreen` digunakan untuk menampilkan form pendaftaran akun baru.
 * Pengguna diminta mengisi data pribadi dan kredensial, kemudian data dikirim ke server untuk proses registrasi.
 *
 * @param navController controller navigasi untuk berpindah ke halaman login setelah berhasil mendaftar.
 */
@Composable
fun RegisterScreen(navController: NavHostController) {
    // State untuk nilai input dari form registrasi
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // State untuk menandai apakah input mengalami kesalahan validasi
    var fullNameError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading selama proses registrasi
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas fokus input dan konteks aplikasi
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Tata letak utama: kolom berisi input dan tombol
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(), // Menyesuaikan layout agar tidak tertutup keyboard
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Input: Nama Lengkap
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                fullNameError = false
            },
            isError = fullNameError,
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth()
        )
        if (fullNameError) {
            Text("Nama lengkap wajib diisi", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input: Username
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = false
            },
            isError = usernameError,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        if (usernameError) {
            Text("Username wajib diisi", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input: Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            isError = emailError,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) {
            Text("Email tidak valid", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input: Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            isError = passwordError,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError) {
            Text("Password wajib diisi", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input: Konfirmasi Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = false
            },
            isError = confirmPasswordError,
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (confirmPasswordError) {
            Text("Password tidak cocok", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Register: memvalidasi form dan mengirim data
        Button(
            onClick = {
                focusManager.clearFocus()

                // Validasi input sebelum submit
                fullNameError = fullName.isBlank()
                usernameError = username.isBlank()
                emailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                passwordError = password.isBlank()
                confirmPasswordError = confirmPassword != password

                // Jika semua input valid, lakukan registrasi
                if (!fullNameError && !usernameError && !emailError && !passwordError && !confirmPasswordError) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val response = ApiClient.instance.register(
                                RegisterRequest(
                                    nm_lengkap = fullName,
                                    email = email,
                                    username = username,
                                    password = password
                                )
                            )
                            isLoading = false
                            val body = response.body()

                            if (response.isSuccessful && body != null) {
                                Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                // Navigasi ke halaman login setelah sukses
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Register.route) { inclusive = true }
                                }
                            } else {
                                val errorMsg = body?.message ?: response.message()
                                Toast.makeText(context, "Gagal: $errorMsg", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            isLoading = false
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navigasi ke halaman login jika sudah punya akun
        TextButton(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sudah punya akun? Login")
        }
    }

    // Dialog loading selama proses registrasi berlangsung
    if (isLoading) {
        AlertDialog(
            onDismissRequest = {}, // Tidak dapat ditutup manual
            confirmButton = {},
            title = { Text("Mohon tunggu") },
            text = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Sedang mengirim data...")
                }
            }
        )
    }
}
