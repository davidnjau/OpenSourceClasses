package com.dnjau.myapplication

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of PatientInformation
        val patientInformation = PatientInformation(this)

        // Create and add EditText fields
        val editTexts = patientInformation.createEditTextWidgetFields()
        val editTextLayout = findViewById<LinearLayout>(R.id.editTextLayout) // Your layout container for EditTexts
        editTexts.forEach { editTextLayout.addView(it) }

        // Create and add Date of Birth section
        val dobSection = patientInformation.createDobSection()
        val dobLayout = findViewById<LinearLayout>(R.id.dobLayout) // Your layout container for DOB section
        dobLayout.addView(dobSection)

        // Create and add Spinner with EditText
        val spinnerWithEditTextLayout = patientInformation.createSpinnerWithEditText()
        val spinnerLayout = findViewById<LinearLayout>(R.id.spinnerLayout) // Your layout container for Spinner and EditText
        spinnerLayout.addView(spinnerWithEditTextLayout)

    }
}