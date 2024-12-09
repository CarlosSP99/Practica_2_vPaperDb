package com.utad.practica_2_v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.dataStore.DataStoreManager
import com.utad.practica_2_v2.databinding.ActivitySingUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="MIS_PREFERENCIAS2")
class SingUpActivity : AppCompatActivity() {

    var name: String = ""
    var password: String = ""
    var rPassword: String = ""
    val dataStore = DataStoreManager.getInstance(this)
    private lateinit var binding: ActivitySingUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listeners()


        binding.btRegistro.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
               dataStore.saveData(name, password)
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listeners(){
        binding.etUser.addTextChangedListener   {
            checkData()
        }
        binding.etPass.addTextChangedListener {
            checkData()
        }
        binding.etRepetirPass.addTextChangedListener {
            checkData()
        }
    }

    private fun checkData() {
        name = binding.etUser.text.toString()
        password = binding.etPass.text.toString()
        rPassword = binding.etRepetirPass.text.toString()
        if (name.isEmpty() || password.isEmpty() || rPassword.isEmpty() || password != rPassword) {
            binding.btRegistro.isEnabled = false
        } else {
            binding.btRegistro.isEnabled = true
        }
    }

}