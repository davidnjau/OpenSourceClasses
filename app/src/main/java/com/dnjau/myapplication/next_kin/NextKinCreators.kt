package com.dnjau.myapplication.next_kin

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dnjau.myapplication.R

// Concrete implementation for label customization (OCP)
class DefaultLabelCustomizer : LabelCustomizer {
    override fun applyCustomization(
        textView: TextView
    ) {
        // Ensure that the TextView has layoutParams
        val layoutParams = textView.layoutParams ?: ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        ) .apply {
            setMargins(8,8,8,0)
        }

        textView.layoutParams = layoutParams
        textView.textSize = 18f

        // Set text color and padding
        textView.setTextColor(Color.BLACK)
        textView.setPadding(16, 16, 16, 16)
    }
}

// Concrete implementation for creating an EditText field
class EditTextFieldCreator(
    private val context: Context
) : FieldCreator {
    override fun createField(
        label: String,
        isMandatory: Boolean,
        inputType: Int
    ): View {
        val editText = EditText(context).apply {
            this.hint = label
            this.inputType = inputType
            this.background = ContextCompat.getDrawable(context, R.drawable.rounded_edittext) // Set rounded border
            this.tag = label

            this.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8,5,8,12)
            }
            this.setPadding(32,16,20,16)
        }
        return editText
    }
}

// Concrete implementation for creating a Spinner field (LSP)
class SpinnerFieldCreator(
    private val options: List<String>,
    private val label: String,
    private val context: Context) : FieldCreator {
    override fun createField(
        label: String,
        isMandatory: Boolean,
        inputType: Int): View {
        val spinner = Spinner(context)
        spinner.tag = label
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        return spinner
    }
}