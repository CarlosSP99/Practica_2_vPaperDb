package com.utad.practica_2_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.dataStore.DataStoreManager
import com.utad.practica_2_v2.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var name: String = ""
    private var password: String = ""
    private val dataStore = DataStoreManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        binding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadDataFromUser()

        listeners()

        binding.btnSingUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogIn.setOnClickListener {
            checkCredentials()
        }
    }

    private fun listeners(){
        binding.etUser.addTextChangedListener {
            checkData()
        }
        binding.etPass.addTextChangedListener {
            checkData()
        }
    }


    private fun checkCredentials(){
        var userDataBase=""
        var passwordDataBase=""


        lifecycleScope.launch {
            dataStore.loadData().collect{
                userProfile-> withContext(Dispatchers.Main){
                    userDataBase= (userProfile.name ?:0).toString()
                    passwordDataBase= (userProfile.password?:0).toString()
                if (name == userDataBase && password == passwordDataBase){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            }
        }

    }

    private fun loadDataFromUser(){
        lifecycleScope.launch {
            dataStore.loadData().collect {userProfile ->
                // Actualiza la interfaz de usuario con los datos del usuario
                withContext(Dispatchers.Main){
                    binding.etUser.setText(userProfile.name)
                    binding.etPass.setText(userProfile.password)
                }

            }
        }

    }

    private fun checkData() {
        name = binding.etUser.text.toString()
        password = binding.etPass.text.toString()
        if (name.isEmpty() || password.isEmpty()) {
            binding.btnLogIn.isEnabled = false
        } else {
            binding.btnLogIn.isEnabled = true
        }
    }

}