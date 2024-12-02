package com.example.wikistormlight.model.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import com.example.wikistormlight.model.deserializators.JSONDeserializerCharacter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.wikistormlight.model.dataclasses.Character
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class Controller private constructor(private val context: Context) {  // Pasamos el contexto al constructor
    private val gson: Gson = GsonBuilder().registerTypeAdapter(Character::class.java, JSONDeserializerCharacter()).create()

    fun getCharacter(nombre: String): Character? {
        val jsonString = readAssetFile(nombre)
        return gson.fromJson(jsonString, Character::class.java)
    }

    fun readAssetFile(fileName: String): String{
        val algo = context.assets.open("${fileName}.json")
        val buffer = ByteArray(algo.available())
        algo.read(buffer)
        return String(buffer)
    }


    fun readAssetImg(fileName: String): ImageBitmap {
        try {
            // Comprobamos si el personaje tiene una imagen asociada
            val character = getCharacter(fileName)

            // Si el personaje tiene imagen asociada y el archivo existe, lo cargamos
            if (character?.img != null && fileExistsInAssets("${fileName}.jpg")) {
                val algo = context.assets.open("${fileName}.jpg")
                val bitmap = BitmapFactory.decodeStream(algo)
                return bitmap.asImageBitmap()
            } else {
                // Si no existe la imagen del personaje, se carga una imagen por defecto
                val algo = context.assets.open("choose_order.webp")
                val bitmap = BitmapFactory.decodeStream(algo)
                return bitmap.asImageBitmap()
            }
        } catch (e: Exception) {
            // En caso de un error, logueamos el error y cargamos la imagen por defecto
            e.printStackTrace()
            val algo = context.assets.open("choose_order.webp")
            val bitmap = BitmapFactory.decodeStream(algo)
            return bitmap.asImageBitmap()
        }
    }

    // Función que verifica si un archivo existe en los assets
    fun fileExistsInAssets(fileName: String): Boolean {
        return try {
            val assetList = context.assets.list("") // Listamos todos los archivos en el directorio assets
            assetList?.contains(fileName) == true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Controller? = null

        fun getInstance(context: Context): Controller {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Controller(context).also { INSTANCE = it }
            }
        }
    }
}