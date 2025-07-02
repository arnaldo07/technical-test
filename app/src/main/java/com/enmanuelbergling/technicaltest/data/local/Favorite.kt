package com.enmanuelbergling.technicaltest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)