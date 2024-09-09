package com.dnjau.myapplication

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dnjau.myapplication.demographics.DemographicsRepository
import com.dnjau.myapplication.demographics.DemographicsViewModel
import com.dnjau.myapplication.demographics.DemographicsViewModelFactory
import com.dnjau.myapplication.shared_components.DbCounty
import com.dnjau.myapplication.shared_components.DbField
import com.dnjau.myapplication.shared_components.DbWidgets
import com.dnjau.myapplication.shared_components.DefaultLabelCustomizer
import com.dnjau.myapplication.shared_components.FieldManager
import com.dnjau.myapplication.shared_components.FormUtils
import com.dnjau.myapplication.shared_components.FieldProcessor
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var fieldManager: FieldManager
    private lateinit var fieldProcessor: FieldProcessor
    private lateinit var relationshipOptions: List<String> // Passed by the user
    private lateinit var nonMandatoryFields: List<DbField> // Passed by the user
    private lateinit var rootLayout:LinearLayout

    private lateinit var countryList: List<String> // Passed by the user
    private lateinit var countyList: List<String> // Passed by the user
    private var subCountyList = ArrayList<String>() // Passed by the user
    private lateinit var formDataViewModel: DemographicsViewModel
    private var dbFieldList = ArrayList<DbField>()

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

        val repository = DemographicsRepository()
        val factory = DemographicsViewModelFactory(repository)

//        val dbField1 = DbField(DbWidgets.EDIT_TEXT.name,"Full Name", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
//        val dbField2 = DbField(DbWidgets.EDIT_TEXT.name,"Email Address", false, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
//        val dbField3 = DbField(DbWidgets.EDIT_TEXT.name,"Phone Number", false, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

        relationshipOptions = listOf("Father", "Mother", "Sibling", "Spouse", "Friend")

        countryList = listOf("Kenya", "Uganda")
        countyList = listOf("Nairobi", "Mombasa")


        val dbField4 = DbField(DbWidgets.SPINNER.name,"Country of Origin", true, null, countryList)
        val dbField5 = DbField(DbWidgets.SPINNER.name,"Country of Residence", true, null, countryList)
        val dbField6 = DbField(DbWidgets.SPINNER.name,"Region/Province/County", true, null, countyList)
        val dbField8 = DbField(DbWidgets.SPINNER.name,"Ward/Village", true, null, subCountyList)

        // Initialize the ViewModel
        formDataViewModel = ViewModelProvider(this, factory)
            .get(DemographicsViewModel::class.java)

        dbFieldList.addAll(listOf(dbField4,dbField5,dbField6))

        // Observe LiveData for changes in form data
        formDataViewModel.formDataLiveData.observe(this) { dbFormData ->
            // Update the UI whenever formData changes
            val spinnerTag = dbFormData.tag
            val spinnerText = dbFormData.text

            subCountyList.clear()

            val subDbCountyList = formDataViewModel.loadSubCounties(spinnerText)
            subDbCountyList.forEach {
                subCountyList.add(it.name)
            }

            val dbField7 = DbField(DbWidgets.SPINNER.name,"Sub County/District", true, null, subCountyList)
            val fieldList = listOf(dbField7)
            FormUtils.populateView(ArrayList(fieldList), rootLayout, fieldManager, this)


            Log.e("---->","<-----")
            println(spinnerText)
            println(subCountyList)
            Log.e("---->","<-----")
        }



//        val dbFieldList = listOf(dbField1, dbField2, dbField3, dbField4)

        FormUtils.populateView(ArrayList(dbFieldList), rootLayout, fieldManager, this)

        // Extract form data and start observing changes
        formDataViewModel.extractFormData(rootLayout)

        findViewById<Button>(R.id.saveButton).setOnClickListener {

            val formData = FormUtils.extractFormData(rootLayout)
            Log.e("----->","<------")
            println(formData)
            Log.e("----->","<------")

        }


    }
}