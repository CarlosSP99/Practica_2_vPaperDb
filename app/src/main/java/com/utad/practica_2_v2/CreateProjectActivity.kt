package com.utad.practica_2_v2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.utad.practica_2_v2.dataStore.AppDatabase
import com.utad.practica_2_v2.dataStore.PaperDbManager
import com.utad.practica_2_v2.databinding.ActivityCreateProjectBinding
import com.utad.practica_2_v2.project.Priority
import com.utad.practica_2_v2.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class CreateProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateProjectBinding
    private lateinit var appDb: AppDatabase
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

        appDb = AppDatabase.getDatabase(this)

        // Implementa la funcionalidad del spinner
        languageOptionConfiguration()

        // Implementa la funcionalidad del calendario
        dateOptionConfiguration()

        // comprueba que todos los campos están bien
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
        lenguage=binding.SeymourSpinner.selectedItem.toString()
        details=binding.etDetails.text.toString()

         //Creación de objeto projecto y guardado en base de datos
        val projectCreated= Project(
            name=title,
            shortDescription = shortDescription,
            date = date,
            priority = priority,
            timeNeeded = timeNeeded,
            language = lenguage,
            details = details
        )
        lifecycleScope.launch(Dispatchers.IO){
            appDb.projectDao().insertProject(projectCreated)
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

    private fun listeners() {
        binding.etTitle.addTextChangedListener { checkAllFields() }
        binding.etShortDescription.addTextChangedListener { checkAllFields() }
        binding.etTimeNeeded.addTextChangedListener { checkAllFields() }
        binding.etDate.addTextChangedListener { checkAllFields() }
        binding.etDetails.addTextChangedListener { checkAllFields() }
        binding.rgPriority.setOnCheckedChangeListener { _, _ -> checkAllFields() }
    }

    private fun checkAllFields() {
        val isTitleValid = binding.etTitle.text.isNotEmpty()
        val isShortDescriptionValid = binding.etShortDescription.text.isNotEmpty()
        val isTimeNeededValid = binding.etTimeNeeded.text.isNotEmpty()
        val isDateValid = binding.etDate.text.isNotEmpty()
        val isDetailsValid = binding.etDetails.text.isNotEmpty()
        val isPriorityValid = binding.rgPriority.checkedRadioButtonId != -1
        val isLenguageValid = binding.SeymourSpinner.selectedItemPosition !=-1
        binding.btnCreate.isEnabled =
            isTitleValid && isShortDescriptionValid && isTimeNeededValid
                    && isDateValid && isDetailsValid && isPriorityValid && isLenguageValid
    }

    private fun languageOptionConfiguration() {
        lifecycleScope.launch {
            val languagesList = withContext(Dispatchers.IO) {
                appDb.languagesDao().getAllLanguagesList()
            }

            val languagesNames = languagesList.map { it.name }

            withContext(Dispatchers.Main) {
                val arrayAdapter = ArrayAdapter(
                    this@CreateProjectActivity,
                    android.R.layout.simple_spinner_item,
                    languagesNames
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val spinner = findViewById<Spinner>(R.id.Seymour_spinner) // Use the correct Spinner ID
                spinner.adapter = arrayAdapter
            }
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