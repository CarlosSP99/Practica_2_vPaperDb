package com.utad.practica_2_v2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityCreateProjectBinding
import com.utad.practica_2_v2.project.Priority
import com.utad.practica_2_v2.project.Project
import kotlinx.coroutines.launch
import java.util.Calendar


class CreateProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateProjectBinding
    val paperDb = PaperDbManager.getInstance(this)
    var title = ""
    var shortDescription = ""
    var date = ""
    var priority : Priority = Priority.LOW
    var timeNeeded = ""
    var lenguage = ""
    var details = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        languageOptionConfiguration()

        dateOptionConfiguration()

        listeners()

        binding.btnCreate.setOnClickListener{
            createNewProject()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createNewProject() {
        // Recogida de datos
        title=binding.etTitle.text.toString()
        shortDescription=binding.etShortDescription.text.toString()
        date=binding.etDate.text.toString()
        priority=priorityType()
        timeNeeded=binding.etTimeNeeded.text.toString()
        timeNeeded=timeNeeded+"h"
        lenguage=binding.autoCompleteTextView.text.toString()
        details=binding.etDetails.text.toString()

        // Creación de objeto projecto y guardado en base de datos
        val projectCreated= Project(title,shortDescription,date,priority,timeNeeded,lenguage,details)
        paperDb.saveDataProject(projectCreated)
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

    private fun listeners() {
        binding.etTitle.addTextChangedListener{
            checkTitle()
        }
        binding.etShortDescription.addTextChangedListener{
            checkShortDescription()
        }
        binding.etTimeNeeded.addTextChangedListener{
            checkPriority()
        }
        binding.etDate.addTextChangedListener{
            checkDate()
        }
        binding.etDetails.addTextChangedListener{
            checkDetails()
        }
        binding.rgPriority.setOnCheckedChangeListener {
            _, _ -> checkPriority()
        }
    }

    private fun checkDetails() {
        if (binding.etDetails.text.isNotEmpty()){
            binding.btnCreate.isEnabled = true
        } else {
            binding.btnCreate.isEnabled = false
        }
    }

    private fun checkPriority() {
        if (binding.rgPriority.checkedRadioButtonId != -1){
            binding.btnCreate.isEnabled = true
        } else {
            binding.btnCreate.isEnabled = false
        }
    }

    private fun checkDate() {
        if (binding.tvDate.text.isNotEmpty()){
            binding.btnCreate.isEnabled = true
        } else {
            binding.btnCreate.isEnabled = false
        }
    }

    private fun checkShortDescription() {
       if (binding.etShortDescription.text.toString().isNotEmpty()){
           binding.btnCreate.isEnabled = true
       } else {
           binding.btnCreate.isEnabled = false
       }
    }

    private fun checkTitle() {
        if(binding.etTitle.text.toString().isNotEmpty()){
            binding.btnCreate.isEnabled = true
        } else {
            binding.btnCreate.isEnabled = false
        }
    }




    private fun languageOptionConfiguration() {
        lifecycleScope.launch {
            val languagesList = paperDb.readLanguagesInDB()
            val listOfLanguages: MutableList<String> = mutableListOf()
            languagesList.forEach { language ->
                listOfLanguages.add(language.name)
                Log.d("Languages", language.name)
            }

            val languages = listOfLanguages.toTypedArray()
            val arrayAdapter =
                ArrayAdapter(this@CreateProjectActivity, R.layout.dropdown_menu, languages)

            val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            autocompleteTV.setAdapter(arrayAdapter)
        }
    }

    private fun dateOptionConfiguration(){
        val datePicker = binding.etDate
        datePicker.setOnClickListener {
            // Obtener la fecha actual para establecer como predeterminada
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Mostrar el DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Actualizar el campo con la fecha seleccionada
                    val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    datePicker.setText(date)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

}