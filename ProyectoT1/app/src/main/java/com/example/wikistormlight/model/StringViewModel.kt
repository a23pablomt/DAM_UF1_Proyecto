package com.example.wikistormlight.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StringViewModel(private val stringDao: StringDao) : ViewModel() {

    private val _strings = mutableStateListOf<String>()
    val strings: List<String> get() = _strings

    init {
        loadStrings()
    }

    private fun loadStrings() {
        viewModelScope.launch {
            _strings.clear()
            _strings.addAll(stringDao.getAllStrings().map { it.value })
        }
    }

    fun addString(newString: String) {
        viewModelScope.launch {
            val stringEntity = StringEntity(value = newString)
            stringDao.insert(stringEntity)
            loadStrings() // Recargar la lista después de insertar
        }
    }
}