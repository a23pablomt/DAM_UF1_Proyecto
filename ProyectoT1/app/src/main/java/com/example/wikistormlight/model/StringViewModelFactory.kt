package com.example.wikistormlight.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StringViewModelFactory(private val stringDao: StringDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StringViewModel::class.java)) {
            return StringViewModel(stringDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}