package com.utad.practica_2_v2.dataStore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utad.practica_2_v2.project.Priority
import com.utad.practica_2_v2.project.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject(project: Project)

    @Query("SELECT * FROM Project")
    fun getAllProjectFlow(): Flow<List<Project>>

    @Query("DELETE FROM Project where id=:id")
    suspend fun deleteProject(id: Int)


    @Query("SELECT * FROM Project WHERE id = :id")
    suspend fun getProject(id: Int): Project

    @Query("""
        UPDATE Project 
        SET name = :name, 
            shortDescription = :shortDescription, 
            date = :date, 
            priority = :priority, 
            timeNeeded = :timeNeeded, 
            details = :details
        WHERE id = :id
    """)
    suspend fun updateProject(
        id: Int,
        name: String,
        shortDescription: String,
        date: String,
        priority: Priority,
        timeNeeded: String,
        details: String
    ): Int
}