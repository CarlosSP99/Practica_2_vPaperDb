package com.utad.practica_2_v2.project

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utad.practica_2_v2.languages.Languages

@Entity
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val shortDescription: String,
    val date: String,
    val priority: Priority,
    val timeNeeded: String,
    val details: String,
    val language: String
    )
