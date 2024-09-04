package com.dnjau.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.Calendar

class PatientInformation(private val context: Context) {

    fun createEditTextWidgetFields(): List<LinearLayout> {
        val widgetLayouts = mutableListOf<LinearLayout>()

        DbEditTextNames.entries.forEach { widget ->
            val linearLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 24, 16, 24) // Add more padding between widgets
                }
            }

            val label = TextView(context).apply {
                text = Utils.getHint(widget).replace("Enter ", "").replace("C", "*") // Update label
                textSize = 16f
                setPadding(0, 0, 0, 8) // Padding below label
            }

            val editText = EditText(context).apply {
                hint = Utils.getHint(widget).replace("C", "*") // Update hint
                inputType = InputType.TYPE_CLASS_TEXT
                background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border

                setPaddingRelative(32, 16, 16, 16) // Apply padding to the EditText (start padding is larger for hint)

                // Custom TextWatcher to manage hint padding
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            setPaddingRelative(32, 16, 16, 16) // Apply padding to hint
                        } else {
                            setPaddingRelative(16, 16, 16, 16) // Apply padding to text
                        }

                        if (widget.name.endsWith("_C") && s.isNullOrEmpty()) { // Check if the field is compulsory
                            error = Utils.getErrorMessage(widget) // Set dynamic error message for compulsory fields
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }

            linearLayout.addView(label)
            linearLayout.addView(editText)

            widgetLayouts.add(linearLayout)
        }

        return widgetLayouts
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDobSection(): LinearLayout {
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
        }

        val estimateRadioButton = RadioButton(context).apply {
            text = "Estimate"
            id = View.generateViewId()
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

    fun createSpinnerWithEditText(): LinearLayout {
        val spinnerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 24, 16, 24) // Add more padding between widgets
        }

        val spinnerLabel = TextView(context).apply {
            text = "Select an Option" // Generic label for Spinner
            textSize = 16f
            setPadding(0, 0, 0, 8) // Padding below the label
        }

        val spinner = Spinner(context).apply {
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item, // Use Android's built-in layout
                IdentificationTypes.entries.map { Utils.getSpinnerLabel(it) }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Use Android's built-in layout
            this.adapter = adapter

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16) // Margin below the spinner dropdown
            }
        }

        val spinnerErrorTextView = TextView(context).apply {
            text = Utils.getSpinnerErrorMessage(IdentificationTypes.OPTION_ONE) // Default message for Spinner
            setTextColor(Color.RED)
            visibility = View.GONE
            setPadding(0, 8, 0, 16) // Padding above and below the error message
        }

        val editTextLabel = TextView(context).apply {
            text = "Enter Number" // Label for EditText
            textSize = 16f
            setPadding(0, 0, 0, 8) // Padding below the label
        }

        val editText = EditText(context).apply {
            hint = "Enter number"
            inputType = InputType.TYPE_CLASS_NUMBER
            background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border
            setPaddingRelative(32, 16, 16, 16) // Apply padding to the EditText (start padding is larger for hint)

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 0) // Margin below the "Enter Number" EditText
            }
        }

        spinnerLayout.addView(spinnerLabel)
        spinnerLayout.addView(spinner)
        spinnerLayout.addView(spinnerErrorTextView)
        spinnerLayout.addView(editTextLabel)
        spinnerLayout.addView(editText)

        return spinnerLayout
    }
}


