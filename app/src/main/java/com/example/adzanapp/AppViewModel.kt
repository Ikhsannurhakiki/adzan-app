package com.example.adzanapp

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adzanapp.data.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

class AppViewModel : ViewModel() {

    private val _prayerScheduleState = MutableStateFlow<PrayTime?>(null)
    val prayerScheduleState: StateFlow<PrayTime?> = _prayerScheduleState
    private var dayOfMonth: String = ""
    private var month: String = ""
    private var year: String = ""


    init {
        fetchPosts()
    }

    private fun fetchPosts( ) {
        viewModelScope.launch {
            val apiService = RetrofitInstance.apiService
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val currentDate = LocalDate.now()
                    dayOfMonth = currentDate.dayOfMonth.toString()
                    month = currentDate.monthValue.toString()
                    year = currentDate.year.toString()
                } else {
                    val calendar = Calendar.getInstance()
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH).toString()
                    month = (calendar.get(Calendar.MONTH) + 1).toString()
                    year = calendar.get(Calendar.YEAR).toString()
                }
            }catch (e: Exception){
                Log.e("error", "Terjadi kesalahan saat mengambil data waktu")
            }
            Log.d("info", year)
            Log.d("info", dayOfMonth)
            Log.d("info", month)
            val posts = apiService.getPrayTime("0403", year, month, dayOfMonth)
            _prayerScheduleState.value = posts
        }
    }
}