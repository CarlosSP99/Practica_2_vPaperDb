package com.utad.practica_2_v2.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.MainActivity
import com.utad.practica_2_v2.R
import com.utad.practica_2_v2.dataStore.AppDatabase
import com.utad.practica_2_v2.databinding.ActivityProjectDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectDetailBinding
    var projectId : Int = 0
    private var title = ""
    private var shortDescription = ""
    private var date = ""
    private  var priority : Priority = Priority.LOW
    private var timeNeeded = ""
    private  var lenguage = ""
    private var details = ""
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        appDb = AppDatabase.getDatabase(this)

        projectId = intent.getIntExtra("id", 1)

        fillActivity(projectId)

        binding.btnCreate.setOnClickListener {
            editProject()
        }
    }


      fun editProject() {
          lifecycleScope.launch(Dispatchers.IO) {
              withContext(Dispatchers.Main) {

                  title = binding.tvTitle.text.toString()
                  shortDescription = binding.etShortDescription.text.toString()
                  date = binding.etDate.text.toString()
                  timeNeeded = binding.etTimeNeeded.text.toString() + "h"
                  details = binding.etDetails.text.toString()

                  priority = priorityType()
                  lifecycleScope.launch(Dispatchers.IO) {
                      appDb.projectDao().updateProject(
                          id = projectId,
                          name = title,
                          shortDescription = shortDescription,
                          date = date,
                          priority = priority,
                          timeNeeded = timeNeeded,
                          details = details
                      )
                  }
                  Log.i("ProjectDetail", "Project updated successfully!")

              }

          }
          val intent = Intent(this, MainActivity::class.java)
          startActivity(intent)

      }


    fun fillActivity(id: Int){
        lifecycleScope.launch(Dispatchers.IO){
            val objectProject = appDb.projectDao().getProject(id)
            withContext(Dispatchers.Main){
                binding.tvTitle.setText(objectProject!!.name)
                binding.etShortDescription.setText(objectProject.shortDescription)
                binding.etDate.setText(objectProject.date)
                val priority = objectProject.priority
                if (priority.equals(Priority.HIGH)){
                    binding.rbHigh.isChecked = true
                }
                if (priority.equals(Priority.MEDIUM)){
                    binding.rbMid.isChecked = true
                }
                if (priority.equals(Priority.LOW)){
                    binding.rbLow.isChecked = true
                }
                binding.etTimeNeeded.setText(objectProject.timeNeeded)
                binding.etDetails.setText(objectProject.details)
                binding.SeymourSpinner.setText(objectProject.language)
            }

        }
    }

    private fun priorityType(): Priority {
        if (binding.rbHigh.isChecked){
            priority= Priority.HIGH
        }
        if (binding.rbMid.isChecked){
            priority= Priority.MEDIUM
        }
        if (binding.rbLow.isChecked){
            priority= Priority.LOW
        }
        return priority
    }


}