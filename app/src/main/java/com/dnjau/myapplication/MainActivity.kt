package com.dnjau.myapplication

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dnjau.myapplication.next_kin.DefaultLabelCustomizer
import com.dnjau.myapplication.next_kin.EditTextFieldCreator
import com.dnjau.myapplication.next_kin.FieldManager
import com.dnjau.myapplication.next_kin.SpinnerFieldCreator
import com.dnjau.myapplication.patient_info.FormUtils
import com.dnjau.myapplication.patient_info.PatientInformation

class MainActivity : AppCompatActivity() {

    private lateinit var fieldManager: FieldManager
    private lateinit var relationshipOptions: List<String> // Passed by the user
    private lateinit var nonMandatoryFields: List<NextOfKinField> // Passed by the user
    private lateinit var rootLayout:LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)

        // Initialize FieldManager with dependencies (inject via constructor or manually)
        fieldManager = FieldManager(DefaultLabelCustomizer(), this)

        // Example data
        relationshipOptions = listOf("Father", "Mother", "Sibling", "Spouse", "Friend")
        nonMandatoryFields = listOf(
            NextOfKinField("Phone Number", InputType.TYPE_CLASS_PHONE),
            NextOfKinField("Email", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        )

        // Add mandatory Full Name
        fieldManager.addTextView(
            "Full Name",
            rootLayout
        )
        fieldManager.addField(
            EditTextFieldCreator(this),
            "Enter Full Name",
            true,
            rootLayout
        )

        // Add mandatory Relationship Spinner
        fieldManager.addTextView(
            "Relationship",
            rootLayout
        )
        fieldManager.addField(
            SpinnerFieldCreator(relationshipOptions , this),
            "Select Relationship",
            true,
            rootLayout
        )

        // Add non-mandatory fields dynamically
        for (field in nonMandatoryFields) {
            fieldManager.addTextView(
                field.label,
                rootLayout
            )
            fieldManager.addField(
                EditTextFieldCreator(this),
                field.label,
                false,
                rootLayout
            )

        }


        findViewById<Button>(R.id.saveButton).setOnClickListener {

//            val formData = FormUtils.extractFormData(rootLayout)
//            Log.e("----->","<------")
//            println(formData)
//            Log.e("----->","<------")

        }


    }
}