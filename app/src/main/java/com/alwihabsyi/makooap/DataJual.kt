package com.alwihabsyi.makooap

data class DataJual(
    val idjual: String? = null,
    val barangjual: String? = null,
    val jumlahbarangjual: String? = null,
    val hargabarangjual: String? = null
)

data class DataLaporan(
    val jumlahbarang: String? = null,
    val totalharga: String? = null
)

data class DataSupplier(
    val idsupp: String? = null,
    val namasupp: String? = null,
    val jenisbrg: String? = null,
    val alamat: String? = null,
    val notelp: String? = null,
    val jumlahbrg: String? = null
)
