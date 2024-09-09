package com.dnjau.myapplication.demographics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnjau.myapplication.shared_components.DbCounty
import com.dnjau.myapplication.shared_components.DbSubCounty

class DemographicsRepository {

    private val _countyLiveData = MutableLiveData<List<DbCounty>>()
    val countyLiveData: LiveData<List<DbCounty>> get() = _countyLiveData

    // Fetch counties from the database or API
    fun getCounties() {
        // Simulate data fetching
        val counties = listOf(
            DbCounty(id = 1, name = "Nairobi"),
            DbCounty(id = 2, name = "Mombasa")
        )
        _countyLiveData.postValue(counties)
    }

    // Fetch sub-counties based on selected county id
    fun getSubCounties(countyName: String):ArrayList<DbSubCounty> {
        // Simulate fetching sub-counties based on county ID
        val counties = listOf(
            DbCounty(id = 1, name = "Nairobi"),
            DbCounty(id = 2, name = "Mombasa")
        )
        val subCounties = ArrayList<DbSubCounty>()
        subCounties.clear()
        if (countyName == "Nairobi"){
            subCounties.addAll(
                listOf(
                    DbSubCounty(1, "Westie"),
                    DbSubCounty(2, "Juja")
                )
            )
        }
        if (countyName == "Mombasa"){
            subCounties.addAll(
                listOf(
                    DbSubCounty(1, "Lamu"),
                    DbSubCounty(2, "Malindi")
                )
            )
        }

        return subCounties
    }
}