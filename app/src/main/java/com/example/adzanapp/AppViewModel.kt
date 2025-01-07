package com.example.adzanapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adzanapp.data.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private val _prayerScheduleState = MutableStateFlow<PrayTime?>(null)
    val prayerScheduleState: StateFlow<PrayTime?> = _prayerScheduleState


    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val apiService = RetrofitInstance.apiService
            val posts = apiService.getPrayTime("0403", "2024", "05","14")
                _prayerScheduleState.value = posts
        }
    }
}