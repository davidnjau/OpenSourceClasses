package com.dnjau.myapplication.patient_info

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.dnjau.myapplication.shared_components.DbDOB
import com.dnjau.myapplication.shared_components.DbEditTextNames
import com.dnjau.myapplication.shared_components.IdentificationTypes
import com.dnjau.myapplication.R
import java.util.Calendar

class PatientInformation(private val context: Context) {

    // Method to create the full patient information section
    @RequiresApi(Build.VERSION_CODES.O)
    fun createDobSection(): View {
        val dobLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 24, 16, 24) // Add more padding between widgets
            }
        }

        val dobLabel = TextView(context).apply {
            text = "Date of Birth"
            textSize = 16f
            setPadding(0, 0, 0, 8) // Padding below the label
        }

        val radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.HORIZONTAL
            setPadding(0, 0, 0, 16) // Add some padding between the radio buttons and the next widget
        }

        val accurateRadioButton = RadioButton(context).apply {
            text = "Accurate"
            id = View.generateViewId()
            tag = "ACCURATE" // Set tag for radio button
        }

        val estimateRadioButton = RadioButton(context).apply {
            text = "Estimate"
            id = View.generateViewId()
            tag = "ESTIMATE" // Set tag for radio button
        }

        radioGroup.addView(accurateRadioButton)
        radioGroup.addView(estimateRadioButton)

        val selectedDateTextView = TextView(context).apply {
            text = "Selected Date: "
            visibility = View.GONE
            setPadding(0, 0, 0, 16) // Padding below the selected date text
        }

        val yearEditText = EditText(context).apply {
            hint = Utils.getHint(DbDOB.YEARS_C).replace("C", "*") // Update hint
            inputType = InputType.TYPE_CLASS_NUMBER
            visibility = View.GONE
            background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border
            setPaddingRelative(32, 16, 16, 16) // Padding inside the EditText
            tag = DbDOB.YEARS_C // Set tag to the corresponding enum

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 0) // Margin between "Enter Years *" and "Enter Months"
            }
        }

        val monthEditText = EditText(context).apply {
            hint = Utils.getHint(DbDOB.MONTHS)
            inputType = InputType.TYPE_CLASS_NUMBER
            visibility = View.GONE
            background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border
            setPaddingRelative(32, 16, 16, 16) // Padding inside the EditText
            tag = DbDOB.MONTHS // Set tag to the corresponding enum

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 0) // Margin between "Enter Months" and "Enter Days"
            }
        }

        val dateEditText = EditText(context).apply {
            hint = Utils.getHint(DbDOB.DAYS)
            inputType = InputType.TYPE_CLASS_NUMBER
            visibility = View.GONE
            background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border
            setPaddingRelative(32, 16, 16, 16) // Padding inside the EditText
            tag = DbDOB.DAYS // Set tag to the corresponding enum

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 0) // Margin below "Enter Days"
            }
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                accurateRadioButton.id -> {
                    yearEditText.visibility = View.GONE
                    monthEditText.visibility = View.GONE
                    dateEditText.visibility = View.GONE
                    selectedDateTextView.visibility = View.VISIBLE
                }
                estimateRadioButton.id -> {
                    yearEditText.visibility = View.VISIBLE
                    monthEditText.visibility = View.VISIBLE
                    dateEditText.visibility = View.VISIBLE
                    selectedDateTextView.visibility = View.GONE
                }
            }
        }

        // Add a DatePicker dialog when "Accurate" is selected
        accurateRadioButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    selectedDateTextView.text = "Selected Date: $dayOfMonth/${month + 1}/$year"
                    selectedDateTextView.visibility = View.VISIBLE
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        dobLayout.addView(dobLabel)
        dobLayout.addView(radioGroup)
        dobLayout.addView(selectedDateTextView)
        dobLayout.addView(yearEditText)
        dobLayout.addView(monthEditText)
        dobLayout.addView(dateEditText)

        return dobLayout
    }

}


