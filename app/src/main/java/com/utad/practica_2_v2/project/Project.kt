package com.utad.practica_2_v2.project

data class Project(
    val name: String,
    val shortDescription: String,
    val date: String,
    val priority: Priority,
    val timeNeeded: String,
    val lenguage: String,
    val details: String
    )
