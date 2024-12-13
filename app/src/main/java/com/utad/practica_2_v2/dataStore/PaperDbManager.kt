package com.utad.practica_2_v2.dataStore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.project.Project
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaperDbManager (private val context: Context){
//    fun saveDataLanguage(language: Languages) {
//        scope.launch{
//            Paper.book("Languages").write(language.name, language)
//        }
//    }
//
//    suspend fun readProjectsInDB(): List<Project> {
//        return withContext(Dispatchers.IO) {
//            val projectList: MutableList<Project> = mutableListOf()
//            val keyList: List<String> = Paper.book("Projects").allKeys
//            keyList.forEach { Project ->
//                val project: Project? = Paper.book("Projects").read<Project>(Project)
//                if (project!=null)
//                    projectList.add(project)
//            }
//            projectList
//        }
//    }
//
//    suspend fun checkProjectByName(name:String): Project?{
//       return withContext(Dispatchers.IO) {
//            Paper.book("Projects").read<Project>(name)
//        }
//    }
//
//    fun saveDataProject(project: Project) {
//        scope.launch{
//            Paper.book("Projects").write(project.name, project)
//        }
//    }
//
//    suspend fun deleteLanguage(language: Languages) {
//        withContext(Dispatchers.IO) {
//            Paper.book("Languages").delete(language.name)
//        }
//    }
//    suspend fun deleteProject(project: Project) {
//        withContext(Dispatchers.IO) {
//            Paper.book("Projects").delete(project.name)
//        }
//    }
//
//    suspend fun readLanguagesInDB(): List<Languages> {
//        return withContext(Dispatchers.IO) {
//            val userList: MutableList<Languages> = mutableListOf()
//            val keyList: List<String> = Paper.book("Languages").allKeys
//            keyList.forEach { key ->
//                val language: Languages? = Paper.book("Languages").read<Languages>(key)
//                if (language != null)
//                    userList.add(language)
//            }
//            userList
//        }
//    }
//
//    companion object{
//        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
//        // Con el objetivo de separar en cierta medida lógica de los datos
//        // y evitar varias instancias dataStore se crea un singleton
//        @SuppressLint("StaticFieldLeak")
//        @Volatile
//        private var INSTANCE: PaperDbManager? = null
//        fun getInstance(context: Context): PaperDbManager {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: PaperDbManager(context).also { INSTANCE = it }
//            }
//        }
//
//    }
}