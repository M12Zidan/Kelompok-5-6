package com.app.kelompok_5_6.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import com.app.kelompok_5_6.components.NoteCard
import com.app.kelompok_5_6.model.viewModel.NotesViewModel

/**
 * Composable yang menampilkan halaman utama (Home) berisi daftar catatan.
 *
 * Komponen ini berinteraksi dengan [NotesViewModel] untuk mendapatkan data catatan.
 * Saat data masih dimuat (kosong), akan ditampilkan indikator loading.
 * Setelah data tersedia, catatan ditampilkan dalam bentuk daftar menggunakan [LazyColumn].
 *
 * @param navController Digunakan untuk navigasi antar halaman dalam aplikasi.
 * @param viewModel ViewModel yang menyediakan dan mengelola state daftar catatan.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NotesViewModel = viewModel()
) {
    // Mengamati state daftar catatan dari ViewModel
    val notesState by viewModel.notes.collectAsState()

    // Kontainer utama dengan padding dan layout fleksibel
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Jika state masih kosong, tampilkan indikator loading
        if (notesState.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Tampilkan daftar catatan jika data sudah tersedia
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notesState) { note ->
                    NoteCard(note = note)
                }
            }
        }
    }
}

/**
 * Preview untuk komponen [HomeScreen] yang ditampilkan di Android Studio Preview.
 *
 * Menggunakan [rememberNavController] sebagai NavController dummy.
 * Catatan: Data asli dari ViewModel tidak dimuat pada preview.
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenView() {
    HomeScreen(
        navController = rememberNavController()
    )
}