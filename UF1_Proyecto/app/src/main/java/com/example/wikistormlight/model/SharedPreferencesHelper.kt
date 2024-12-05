package com.example.wikistormlight.model

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Guardar lista de strings
    fun saveStringList(key: String, stringList: List<String>) {
        // Convertir la lista a un String separado por comas
        val joinedStrings = stringList.joinToString(",")
        editor.putString(key, joinedStrings)
        editor.apply()
    }

    // Leer lista de strings
    fun getStringList(key: String): List<String> {
        val savedString = sharedPreferences.getString(key, "")
        return savedString?.split(",")?.toList() ?: emptyList()
    }
}