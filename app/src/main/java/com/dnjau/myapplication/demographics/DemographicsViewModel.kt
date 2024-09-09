package com.dnjau.myapplication.demographics

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dnjau.myapplication.shared_components.DbCounty
import com.dnjau.myapplication.shared_components.DbFormData
import com.dnjau.myapplication.shared_components.DbSubCounty
import kotlinx.coroutines.runBlocking

class DemographicsViewModel(private val repository: DemographicsRepository) : ViewModel() {

    // LiveData that holds the latest DbFormData
    private val _formDataLiveData = MutableLiveData<DbFormData>()
    val formDataLiveData: LiveData<DbFormData> = _formDataLiveData

    // This will be used to keep track of form data
    private val formDataList = ArrayList<DbFormData>()

    // Fetch counties through the repository
    fun loadCounties() {
        repository.getCounties()
    }

    // Fetch sub-counties based on selected county ID
    fun loadSubCounties(countyId: String) = runBlocking{
        repository.getSubCounties(countyId)
    }

    // Extracts the form data and updates LiveData whenever a spinner changes
    fun extractFormData(rootLayout: LinearLayout) {
        // Traverse through all child views of rootLayout
        for (i in 0 until rootLayout.childCount) {
            when (val childView = rootLayout.getChildAt(i)) {

                is Spinner -> {
                    // Add an event listener for when the spinner value is changed
                    childView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            // Get the updated text when a new item is selected
                            val updatedText = parent.getItemAtPosition(position)

                            val tag = childView.tag
                            if (tag != null && updatedText != null) {
                                val formData = DbFormData(tag.toString(), updatedText.toString())

                                // Update the formDataList with the new entry
                                formDataList.add(formData)

                                // Set the latest selected item as LiveData
                                _formDataLiveData.value = formData
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // Handle case where no item is selected, if necessary
                        }
                    }
                }
            }
        }
    }
}

class DemographicsViewModelFactory(
    private val repository: DemographicsRepository // Pass any dependencies here
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DemographicsViewModel::class.java)) {
            return DemographicsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
