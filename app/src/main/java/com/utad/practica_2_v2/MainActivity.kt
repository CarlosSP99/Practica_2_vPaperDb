package com.utad.practica_2_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityMainBinding
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.languages.LanguagesAdapter
import com.utad.practica_2_v2.project.Project
import com.utad.practica_2_v2.project.ProjectAdapter
import com.utad.practica_2_v2.project.ProjectDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var projectList = mutableListOf<Project>()
    private lateinit var adapter: ProjectAdapter

    val paperDb = PaperDbManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvProjects.layoutManager = LinearLayoutManager(this)
        adapter = ProjectAdapter(projectList){ project ->
            val intent = Intent(this, ProjectDetailActivity::class.java)
            intent.putExtra("name", project.name)
            startActivity(intent)
        }


        binding.btnCrear.setOnClickListener {
            val intent = Intent(this, CreateProjectActivity::class.java)
            startActivity(intent)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toolbar_option -> {
                    val intent = Intent(this, LanguagesActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        readProjectsInDB()
    }

    private fun readProjectsInDB() {
        lifecycleScope.launch(Dispatchers.IO) {
            val project = paperDb.readProjectsInDB()
            withContext(Dispatchers.Main) {
                if (project.isNotEmpty()) {
                    adapter.submitList(project.toMutableList())
                    binding.rvProjects.adapter = adapter
                } else {
                    binding.rvProjects.adapter = adapter
                }
            }
        }


    }
}