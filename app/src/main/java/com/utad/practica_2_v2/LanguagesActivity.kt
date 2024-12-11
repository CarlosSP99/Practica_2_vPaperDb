package com.utad.practica_2_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.dataStore.AppDatabase
import com.utad.practica_2_v2.databinding.ActivityLanguagesBinding
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.languages.LanguagesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LanguagesActivity : AppCompatActivity() {

    private lateinit var adapter: LanguagesAdapter
    private var languagesList = mutableListOf<Languages>()
    private lateinit var binding: ActivityLanguagesBinding
    private lateinit var appDb: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLanguagesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        appDb = AppDatabase.getDatabase(this)

        innitRecyclerView()

        readLanguagesInDB()

        binding.btnCrear.setOnClickListener {
            val intent = Intent(this, CreateLaguagesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun innitRecyclerView() {
        binding.rvLanguages.layoutManager = LinearLayoutManager(this)
        adapter = LanguagesAdapter(languagesList)
    }


    // necesita un flujo de datos para actualizar en tiempo real
    private fun readLanguagesInDB() {
        lifecycleScope.launch(Dispatchers.IO) {
            appDb.languagesDao().getAllLanguages().collect{
                withContext(Dispatchers.Main) {
                    adapter.submitList(it.toMutableList())
                    binding.rvLanguages.adapter = adapter
                }
            }
        }


    }
}


