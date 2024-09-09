package com.dnjau.myapplication.shared_components

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView


class FieldManager(
    private val labelCustomizer: LabelCustomizer,
    private val context: Context) {

    fun addTextView(
        label: String,
        isMandatory: Boolean,
        parentLayout: LinearLayout) {
        val labelText = if (isMandatory) "$label *" else label
        val textView = TextView(context).apply {
            text = labelText
        }
        labelCustomizer.applyCustomization(textView)
        parentLayout.addView(textView)
    }

    fun addField(
        fieldCreator: FieldCreator,
        label: String,
        isMandatory: Boolean,
        parentLayout: LinearLayout) {

        val field = fieldCreator.createField(
            label,
            isMandatory
        )
        parentLayout.addView(field)
    }
}

