package com.dnjau.myapplication.next_kin

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.dnjau.myapplication.DbField
import com.dnjau.myapplication.DbWidgets


class FieldManager(
    private val labelCustomizer: LabelCustomizer,
    private val context: Context) {

    private fun addTextView(
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

    private fun addField(
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

    fun populateView(
        dbFieldList:ArrayList<DbField>,
        rootLayout:LinearLayout
        ){

        dbFieldList.forEach {
            val widget = it.widgets
            val optionList = it.optionList
            val label = it.label

            // Add mandatory Full Name
            addTextView(
                it.label,
                it.isMandatory,
                rootLayout
            )

            val fieldWidgets = when (widget) {
                DbWidgets.EDIT_TEXT.name -> {
                    EditTextFieldCreator(context)
                }
                DbWidgets.SPINNER.name -> {
                    SpinnerFieldCreator(
                        optionList ,
                        label,
                        context
                    )
                }
                else -> {
                    null
                }
            }
            if (fieldWidgets != null){
                addField(
                    fieldWidgets,
                    it.label,
                    it.isMandatory,
                    rootLayout
                )
            }
        }
    }
}

