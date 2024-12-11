package com.utad.practica_2_v2.languages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Languages(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
