package com.utad.practica_2_v2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.dataStore.AppDatabase
import com.utad.practica_2_v2.dataStore.DataStoreManager
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityCreateLaguagesBinding
import com.utad.practica_2_v2.languages.Languages
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateLaguagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLaguagesBinding
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateLaguagesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        listeners()

        binding.btnCrear.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                 val languageName = binding.etNameLanguague.text.toString()
                val language = Languages(name=languageName)
                appDb.languagesDao().insert(language)
                finish()
            }
         }
    }

    private fun listeners() {
        binding.etNameLanguague.addTextChangedListener {
            checkData()
        }
    }

    private fun checkData() {
        if(binding.etNameLanguague.text.toString().isNotEmpty()){
            binding.btnCrear.isEnabled = true
        } else {
            binding.btnCrear.isEnabled = false
        }
    }
}