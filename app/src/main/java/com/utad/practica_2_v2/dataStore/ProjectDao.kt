package com.utad.practica_2_v2.dataStore

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.project.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject (project: Project)

    @Query("SELECT * FROM Project")
     fun getAllProjectFlow(): Flow<List<Project>>

    @Query("DELETE FROM Project where id=:id")
    suspend fun deleteProject(id: Int)


    @Query("Select * from Languages where name=:langugeParam")
    suspend fun getLanguage(langugeParam: String): Languages
}