package com.example.adzanapp.data.service

import com.example.adzanapp.PrayTime
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("sholat/jadwal/{kota}/{tahun}/{bulan}/{tanggal}")
    suspend fun getPrayTime(
        @Path("kota") kota: String,
        @Path("tahun")tahun: String,
        @Path("bulan")bulan: String,
        @Path("tanggal")tanggal: String
    ): PrayTime
}