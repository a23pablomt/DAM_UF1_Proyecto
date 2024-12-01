package com.example.wikistormlight.model.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.wikistormlight.model.deserializators.JSONDeserializerCharacter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.wikistormlight.model.dataclasses.Character
import java.io.FileReader
import java.io.InputStream
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class Controller private constructor(private val context: Context) {  // Pasamos el contexto al constructor
    private val gson: Gson = GsonBuilder().registerTypeAdapter(Character::class.java, JSONDeserializerCharacter()).create()

    fun getCharacter(nombre: String): Character? {
        val jsonString = readAssetFile(nombre)
        return gson.fromJson(jsonString, Character::class.java)
    }

//    private fun readAssetFile(fileName: String): String {
//        return try {
//            val assetManager = context.assets
//            val inputStream = assetManager.open("sampledata/cosmere/output/${fileName}.json")
//            inputStream.bufferedReader().use { it.readText() }
//        } catch (e: Exception) {
//            e.printStackTrace()  // Para depuración
//            ""
//        }
//    }

    fun readAssetFile(fileName: String): String{
        val algo = context.assets.open("${fileName}.json")
        val buffer = ByteArray(algo.available())
        algo.read(buffer)
        return String(buffer)
    }

    fun readAssetImg(fileName: String): ImageBitmap {
        val algo = context.assets.open("${fileName}.jpg")
        val bitmap = BitmapFactory.decodeStream(algo)
        return bitmap.asImageBitmap()
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