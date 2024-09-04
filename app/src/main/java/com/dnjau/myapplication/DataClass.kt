package com.dnjau.myapplication

enum class DbEditTextNames {
    FIRST_NAME_C, LAST_NAME_C, MIDDLE_NAME, OTHER_NAMES, TELEPHONE_C
}
enum class DbDOB {
    YEARS_C, MONTHS, DAYS
}
enum class IdentificationTypes {
    PASSPORT, NEMIS, NATIONAL_ID
}

data class FormData(
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val otherNames: String?,
    val telephone: String?,
    val years: Int?,
    val months: Int?,
    val days: Int?,
    val selectedDate: String?,
    val identificationType: IdentificationTypes?,
    val number: Int?
)

