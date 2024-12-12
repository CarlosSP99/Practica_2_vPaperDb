package com.utad.practica_2_v2.dataStore

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.project.Project

@Database(entities = [Languages::class, Project::class], version = 2 )
abstract class  AppDatabase: RoomDatabase(){
        abstract fun languagesDao():LanguageDao
        abstract fun projectDao():ProjectDao

        companion object{
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context):AppDatabase{
                val tempInstance = INSTANCE
                if (tempInstance!=null){return tempInstance}
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
                    INSTANCE=instance
                    return instance
                }
            }
        }
    }