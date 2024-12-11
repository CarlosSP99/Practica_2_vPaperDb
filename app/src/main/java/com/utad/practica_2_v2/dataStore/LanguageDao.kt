package com.utad.practica_2_v2.dataStore

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utad.practica_2_v2.languages.Languages
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao{
    @Insert
    suspend fun insert (language: Languages)

    @Query("SELECT * FROM Languages")
     fun getAllLanguages(): Flow<List<Languages>>

    @Query("SELECT * FROM Languages")
    fun getAllLanguagesList(): List<Languages>
}