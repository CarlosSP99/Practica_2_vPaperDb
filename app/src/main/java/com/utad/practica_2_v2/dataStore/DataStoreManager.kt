package com.utad.practica_2_v2.dataStore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.utad.practica_2_v2.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {

     suspend fun saveData(name: String, password: String) {
         context.dataStore.edit { editor ->
             editor[USER_FIRST_NAME] = name
             editor[USER_PASSWORD] = password
         }
     }

     fun loadData(): Flow<User> {
         return context.dataStore.data.map { preferences ->
             User(
                 name = preferences[USER_FIRST_NAME] ?: "",
                 password = preferences[USER_PASSWORD] ?: ""
             )
         }
     }


     companion object{
        val DATA_STORE_NAME="MIS_PREFERENCIAS2"
        val USER_FIRST_NAME = stringPreferencesKey("name")
        val USER_PASSWORD = stringPreferencesKey("password")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

         // Con el objetivo de separar en cierta medida lógica de los datos
         // y evitar varias instancias dataStore se crea un singleton
         @SuppressLint("StaticFieldLeak")
         @Volatile
         private var INSTANCE: DataStoreManager? = null
         fun getInstance(context: Context): DataStoreManager {
             return INSTANCE ?: synchronized(this) {
                 INSTANCE ?: DataStoreManager(context).also { INSTANCE = it }
             }
         }

    }
}