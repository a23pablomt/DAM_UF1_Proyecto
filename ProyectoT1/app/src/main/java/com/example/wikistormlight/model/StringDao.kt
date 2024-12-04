package com.example.wikistormlight.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StringDao {

    @Insert
    suspend fun insert(stringEntity: StringEntity)

    @Query("SELECT * FROM strings_table")
    suspend fun getAllStrings(): List<StringEntity>
}