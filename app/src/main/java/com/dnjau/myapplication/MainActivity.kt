package com.dnjau.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private val linearLayoutList = ArrayList<LinearLayout>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of PatientInformation
        val patientInformation = PatientInformation(this)

        // Get the root layout from XML
        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)

// Create and add all widgets to the root layout
        patientInformation.createFullPatientInformationSection(rootLayout)

        findViewById<Button>(R.id.saveButton).setOnClickListener {

            val formData = FormUtils.extractFormData(rootLayout)
            Log.e("----->","<------")
            println(formData)
            Log.e("----->","<------")

        }


    }
}