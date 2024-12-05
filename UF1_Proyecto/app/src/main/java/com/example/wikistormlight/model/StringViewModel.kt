package com.example.wikistormlight.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class StringViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferencesHelper = SharedPreferencesHelper(context)

    // Lista que se almacenará y mostrará
    var strings by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        loadStrings()
    }

    // Cargar la lista de strings desde SharedPreferences
    private fun loadStrings() {
        strings = sharedPreferencesHelper.getStringList("string_list_key")
    }

    // Agregar un nuevo string y guardar la lista actualizada
    fun addString(newString: String) {
        val updatedList = strings.toMutableList().apply {
            add(newString)
        }
        strings = updatedList
        sharedPreferencesHelper.saveStringList("string_list_key", updatedList)
    }

    fun deleteString(stringToDelete: String) {
        val updatedList = strings.toMutableList().apply {
            remove(stringToDelete) // Remove the string from the list
        }
        strings = updatedList
        sharedPreferencesHelper.saveStringList("string_list_key", updatedList) // Save the updated list
    }
}