package com.app.kelompok_5_6.model.response

/**
 * Response utama dari API saat mengambil data catatan (notes).
 *
 * @property code Kode status response dari server (contoh: 200 untuk sukses).
 * @property message Pesan deskriptif dari hasil response.
 * @property data Objek yang memuat daftar catatan dalam bentuk [NotesData].
 */
data class NotesResponse(
    val code: Int,
    val message: String,
    val data: NotesData
)

/**
 * Struktur pembungkus yang berisi daftar catatan.
 *
 * @property notes Daftar catatan yang diterima dari server.
 */
data class NotesData(
    val notes: List<NoteItem>
)

/**
 * Representasi satu item catatan (note) yang dikirim dari server.
 *
 * @property nm_lengkap Nama lengkap pemilik catatan.
 * @property id_notes ID unik dari catatan.
 * @property id_user ID pengguna yang membuat catatan.
 * @property title Judul catatan.
 * @property content Isi atau deskripsi lengkap dari catatan.
 * @property created_at Tanggal dan waktu saat catatan dibuat (format ISO 8601).
 * @property updated_at Tanggal dan waktu saat catatan terakhir diperbarui (format ISO 8601).
 */
data class NoteItem(
    val nm_lengkap: String,
    val id_notes: String,
    val id_user: String,
    val title: String,
    val content: String,
    val created_at: String,
    val updated_at: String
)