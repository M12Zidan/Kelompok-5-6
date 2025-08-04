package com.app.kelompok_5_6.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.kelompok_5_6.model.request.NoteCreateRequest
import com.app.kelompok_5_6.navigation.Screen
import com.app.kelompok_5_6.service.api.ApiClient
import com.app.kelompok_5_6.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun CreateNoteScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("📝 Tambah Catatan", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    val token = PreferenceManager.getToken(context)
                    if (token.isNullOrEmpty()) {
                        Toast.makeText(context, "Token tidak ditemukan. Harap login ulang.", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    try {
                        val response = ApiClient.instance.createNotes(
                            token = "Bearer $token",
                            request = NoteCreateRequest(title, content)
                        )

                        if (response.isSuccessful) {
                            Toast.makeText(context, "Catatan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.Home.route)
                        } else {
                            Toast.makeText(context, "Gagal menambahkan catatan", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
