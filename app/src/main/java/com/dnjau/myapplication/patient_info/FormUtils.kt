package com.dnjau.myapplication.patient_info

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.dnjau.myapplication.DbDOB
import com.dnjau.myapplication.DbEditTextNames
import com.dnjau.myapplication.DbFormData
import com.dnjau.myapplication.FormData
import com.dnjau.myapplication.IdentificationTypes

object FormUtils {
    fun extractFormData(rootLayout: LinearLayout): ArrayList<DbFormData> {


        val formDataList = ArrayList<DbFormData>()

        // Traverse through all child views of rootLayout
        for (i in 0 until rootLayout.childCount) {

            when (val childView = rootLayout.getChildAt(i)) {
                is EditText -> {
                    val tag = childView.tag.toString()
                    val text = childView.text.toString()
                    formDataList.add(DbFormData(tag, text))
                }
                is Spinner -> {
                    // Extract selected identification type from spinner
                    val text = childView.selectedItem.toString()
                    val tag = childView.tag.toString()
                    formDataList.add(DbFormData(tag, text))
                }
            }
        }

        return formDataList
    }

}