package com.utad.practica_2_v2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityLanguagesBinding
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.languages.LanguagesAdapter
import com.utad.practica_2_v2.languages.LanguagesProvider
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LanguagesActivity : AppCompatActivity() {

    private lateinit var adapter: LanguagesAdapter
    private var languagesList = mutableListOf<Languages>()
    private lateinit var binding: ActivityLanguagesBinding
    private val paperDb = PaperDbManager.getInstance(this)

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

        innitRecyclerView()

        binding.btnCrear.setOnClickListener {
            val intent = Intent(this, CreateLaguagesActivity::class.java)
            startActivity(intent)
        }
    }

    fun innitRecyclerView() {
        binding.rvLanguages.layoutManager = LinearLayoutManager(this)
        adapter = LanguagesAdapter(languagesList)
    }


    override fun onResume() {
        super.onResume()
        readLanguagesInDB()
    }

    private fun readLanguagesInDB() {
        lifecycleScope.launch(Dispatchers.IO) {
            val lang = paperDb.readLanguagesInDB()
            withContext(Dispatchers.Main) {
                if (lang.isNotEmpty()) {
                    adapter.submitList(lang.toMutableList())
                    binding.rvLanguages.adapter = adapter
                } else {
                    binding.rvLanguages.adapter = adapter
                }
            }
        }
    }


}