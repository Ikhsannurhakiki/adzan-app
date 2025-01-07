package com.example.adzanapp.data.model


data class Request(
    val path: String
)

data class PrayTime(
    val status: Boolean,
    val request: Request,
    val data: Responses
)
data class Responses(
    val id: Int,
    val lokasi: String,
    val daerah: String,
    val jadwal: Jadwal
)
data class Jadwal(
    val tanggal: String,
    val imsak: String,
    val subuh: String,
    val terbit: String,
    val dhuha: String,
    val dzuhur: String,
    val ashar: String,
    val maghrib: String,
    val isya: String,
    val date: String
)
