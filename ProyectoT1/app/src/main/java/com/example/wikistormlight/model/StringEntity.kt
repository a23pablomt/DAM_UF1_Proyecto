package com.example.wikistormlight.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strings_table")
data class StringEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: String
)