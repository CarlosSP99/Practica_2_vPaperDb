package com.utad.practica_2_v2.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.MainActivity
import com.utad.practica_2_v2.R
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityProjectDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProjectDetailBinding
    private val paperDb = PaperDbManager.getInstance(this)
    var title = ""
    var shortDescription = ""
    var date = ""
    var priority : Priority = Priority.LOW
    var timeNeeded = ""
    var lenguage = ""
    var details = ""

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

        val name = intent.getStringExtra("name")

        fillingActivity(name!!)

        binding.btnCreate.setOnClickListener {
            editProject()
        }
    }

    private fun editProject() {
         title=binding.tvTitle.text.toString()
         shortDescription=binding.etShortDescription.text.toString()
         date=binding.etDate.text.toString()
         timeNeeded=binding.etTimeNeeded.text.toString()
         details=binding.etDetails.text.toString()
         lenguage=binding.autoCompleteTextView.text.toString()
         priority=priorityType()
        paperDb.saveDataProject(Project(title,shortDescription,date,priority,timeNeeded,lenguage,details))
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    fun fillingActivity(name: String){
        lifecycleScope.launch(Dispatchers.IO){
            val objectProject = paperDb.checkProjectByName(name)
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
                binding.autoCompleteTextView.setText(objectProject.lenguage.toString())
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