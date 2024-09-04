package com.dnjau.myapplication

import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

object FormUtils {
    fun extractFormData(rootLayout: LinearLayout): FormData {
        var firstName: String? = null
        var lastName: String? = null
        var middleName: String? = null
        var otherNames: String? = null
        var telephone: String? = null
        var years: Int? = null
        var months: Int? = null
        var days: Int? = null
        var selectedDate: String? = null
        var identificationType: IdentificationTypes? = null
        var number: Int? = null

        // Traverse through all child views of rootLayout
        for (i in 0 until rootLayout.childCount) {
            when (val childView = rootLayout.getChildAt(i)) {
                is LinearLayout -> {
                    // Traverse through all child views of the LinearLayout
                    for (j in 0 until childView.childCount) {
                        when (val innerView = childView.getChildAt(j)) {
                            is EditText -> {
                                val tag = innerView.tag
                                when (tag) {
                                    DbEditTextNames.FIRST_NAME_C -> firstName = innerView.text.toString()
                                    DbEditTextNames.LAST_NAME_C -> lastName = innerView.text.toString()
                                    DbEditTextNames.MIDDLE_NAME -> middleName = innerView.text.toString()
                                    DbEditTextNames.OTHER_NAMES -> otherNames = innerView.text.toString()
                                    DbEditTextNames.TELEPHONE_C -> telephone = innerView.text.toString()
                                    DbDOB.YEARS_C -> years = innerView.text.toString().toIntOrNull()
                                    DbDOB.MONTHS -> months = innerView.text.toString().toIntOrNull()
                                    DbDOB.DAYS -> days = innerView.text.toString().toIntOrNull()
                                }
                            }
                            is TextView -> {
                                // Assuming the TextView is used to display selected date
                                if (innerView.text.startsWith("Selected Date: ")) {
                                    selectedDate = innerView.text.toString().removePrefix("Selected Date: ")
                                }
                            }
                            is Spinner -> {
                                // Extract selected identification type from spinner
                                val selectedItem = innerView.selectedItem.toString()
                                identificationType = IdentificationTypes.entries.find { Utils.getSpinnerLabel(it) == selectedItem }
                            }
                        }
                    }
                }
            }
        }

        return FormData(
            firstName,
            lastName,
            middleName,
            otherNames,
            telephone,
            years,
            months,
            days,
            selectedDate,
            identificationType,
            number
        )
    }

}