package com.example.wikistormlight.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StringEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stringDao(): StringDao
}