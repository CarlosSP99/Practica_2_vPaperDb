package com.utad.practica_2_v2.project

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utad.practica_2_v2.languages.Languages

@Entity
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String="0",
    val shortDescription: String="0",
    val date: String="0",
    val priority: Priority=Priority.HIGH,
    val timeNeeded: String="0",
    val details: String="0",
    val language: String="0"
    )
