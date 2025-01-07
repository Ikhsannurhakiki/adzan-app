package com.example.adzanapp

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adzanapp.data.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class AppViewModel : ViewModel() {

    private val _prayerScheduleState = MutableStateFlow<PrayTime?>(null)
    val prayerScheduleState: StateFlow<PrayTime?> = _prayerScheduleState
    private var dayOfMonth: String = ""
    private var month: String = ""
    private var year: String = ""
    private lateinit var formattedDate: String


    init {
        getTime()
        fetchPosts(year, month, dayOfMonth)
    }
    fun timeId(): String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))
            formattedDate = currentDate.format(formatter)
        }else{
            val calendar = Calendar.getInstance()
            val customDate = calendar.time
            val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
            formattedDate = formatter.format(customDate)
        }
        return formattedDate
    }
    private fun getTime(){
        viewModelScope.launch {
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
            } catch (e: Exception) {
                Log.e("error", "Terjadi kesalahan saat mengambil data waktu")
            }
        }
    }
    private fun fetchPosts(year: String, month:String, dayOfMonth:String) {
        viewModelScope.launch {
            val apiService = RetrofitInstance.apiService
            val posts = apiService.getPrayTime("0403", year, month, dayOfMonth)
            _prayerScheduleState.value = posts
        }
    }
}