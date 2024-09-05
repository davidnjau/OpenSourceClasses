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
    private lateinit var nonMandatoryFields: List<DbField> // Passed by the user
    private lateinit var rootLayout:LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)

        // Initialize FieldManager with dependencies (inject via constructor or manually)
        fieldManager = FieldManager(DefaultLabelCustomizer(), this)

        /**
         * 1. Edit Texts
         * - Provide the label and the input type
         * -
         */


        val dbField1 = DbField(DbWidgets.EDIT_TEXT.name,"Full Name", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        val dbField2 = DbField(DbWidgets.EDIT_TEXT.name,"Email Address", false, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        val dbField3 = DbField(DbWidgets.EDIT_TEXT.name,"Phone Number", false, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

        relationshipOptions = listOf("Father", "Mother", "Sibling", "Spouse", "Friend")

        val dbField4 = DbField(DbWidgets.SPINNER.name,"Relationships", true, null, relationshipOptions)

        val dbFieldList = listOf(dbField1, dbField2, dbField3, dbField4)
        fieldManager.populateView(ArrayList(dbFieldList), rootLayout)


        findViewById<Button>(R.id.saveButton).setOnClickListener {

            val formData = FormUtils.extractFormData(rootLayout)
            Log.e("----->","<------")
            println(formData)
            Log.e("----->","<------")

        }


    }
}