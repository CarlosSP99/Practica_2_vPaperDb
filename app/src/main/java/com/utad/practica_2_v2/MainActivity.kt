package com.utad.practica_2_v2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.utad.practica_2_v2.dataStore.AppDatabase
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityMainBinding
import com.utad.practica_2_v2.languages.Languages
import com.utad.practica_2_v2.languages.LanguagesAdapter
import com.utad.practica_2_v2.project.Priority
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
    private lateinit var appDb: AppDatabase

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


        appDb = AppDatabase.getDatabase(this)

        innitRecyclerView()

        readProjectsInDB()

        // el metodo no funciona porq no le da la gana no puedo hacer mucho más
        projectList.sortByDescending { getPriority(it.priority) }

        binding.btnCrear.setOnClickListener {
           val intent = Intent(this, CreateProjectActivity::class.java)
           startActivity(intent)
        }

         binding.toolbar.setOnMenuItemClickListener({ menuItem ->
             when (menuItem.itemId) {
                 R.id.toolbar_option -> {
                     val intent = Intent(this, LanguagesActivity::class.java)
                     startActivity(intent)
                     true
                 }
                 else -> false
             }
         })
        }


    private fun innitRecyclerView() {
        binding.rvProjects.layoutManager = LinearLayoutManager(this)
        adapter = ProjectAdapter(projectList, onClickListener = { project ->
            val intent = Intent(this, ProjectDetailActivity::class.java)
            intent.putExtra("id", project.id.toInt()) // Convierte a Int si es necesario.
            startActivity(intent)
        }, onDeleteClickListener = { project ->

            val alert = AlertDialog.Builder(this)
                .setTitle("Eliminar ${project.name}")
                .setMessage("¿Estas seguro de que quieres eliminar ${project.name}?")
                .setNeutralButton("Cerrar", null)
                .setPositiveButton("Aceptar") { _, _ ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        appDb.projectDao().deleteProject(project.id)
                        withContext(Dispatchers.Main) {
                            adapter.notifyDataSetChanged()
                            readProjectsInDB()
                        }
                    }
                    display("Se ha eliminado ${project.name}")
                }.create()
            alert.show()

        })
    }

    private fun readProjectsInDB() {
        lifecycleScope.launch(Dispatchers.IO) {
            appDb.projectDao().getAllProjectFlow().collect{
                withContext(Dispatchers.Main) {
                    adapter.submitList(it.toMutableList())
                    binding.rvProjects.adapter = adapter
                }
            }

        }
    }


        fun getPriority(priority:Priority): Int {
            return when (priority) {
                Priority.LOW -> 3
                Priority.MEDIUM -> 1
                Priority.HIGH -> 2
            }
        }
    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}