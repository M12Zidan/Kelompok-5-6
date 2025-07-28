package com.app.kelompok_5_6.model.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kelompok_5_6.model.response.NoteItem
import com.app.kelompok_5_6.service.api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel yang bertanggung jawab untuk mengelola dan menyediakan data catatan (notes)
 * ke UI layer menggunakan pendekatan reactive berbasis StateFlow.
 *
 * ViewModel ini akan secara otomatis mengambil data catatan saat diinisialisasi.
 */
class NotesViewModel : ViewModel() {

    /**
     * State internal yang menyimpan daftar catatan yang dapat berubah sewaktu-waktu.
     * Hanya dapat diubah di dalam ViewModel.
     */
    private val _notes = MutableStateFlow<List<NoteItem>>(emptyList())

    /**
     * State publik yang terekspos ke UI untuk mengamati daftar catatan.
     * Bersifat immutable agar aman digunakan di luar ViewModel.
     */
    val notes: StateFlow<List<NoteItem>> = _notes

    /**
     * Fungsi untuk mengambil data catatan dari API secara asynchronous
     * menggunakan coroutine. Data yang berhasil diambil akan disimpan
     * ke dalam [_notes]. Jika terjadi error, akan dicatat ke log sistem.
     */
    private fun getNotes() {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getAllNotes()
                _notes.value = response.data.notes
            } catch (e: Exception) {
                Log.e("GetNotesError", "Gagal Mengambil Data Catatan", e)
            }
        }
    }

    /**
     * Blok inisialisasi yang dipanggil saat ViewModel pertama kali dibuat.
     * Memicu pengambilan data catatan dari server.
     */
    init {
        getNotes()
    }
}