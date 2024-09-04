package com.dnjau.myapplication

object Utils {

    private val hints: Map<Any, String>
    private val errorMessages: Map<Any, String>

    init {
        hints = DbEditTextNames.entries.associateWith { widget ->
            "Enter ${formatEnumName(widget.name)}"
        } + DbDOB.entries.associateWith { widget ->
            "Enter ${formatEnumName(widget.name)}"
        }

        errorMessages = DbEditTextNames.entries.associateWith { widget ->
            "${formatEnumName(widget.name)} is required"
        } + DbDOB.entries.associateWith { widget ->
            "${formatEnumName(widget.name)} is required"
        }
    }

    private fun formatEnumName(enumName: String): String {
        return enumName.split("_").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    fun getHint(key: Any): String {
        return hints[key] ?: ""
    }

    fun getErrorMessage(key: Any): String {
        return errorMessages[key] ?: "This field is required"
    }
    fun getSpinnerLabel(key: IdentificationTypes): String {
        return formatEnumName(key.name)
    }

    fun getSpinnerErrorMessage(key: IdentificationTypes): String {
        return "${formatEnumName(key.name)} selection is required"
    }
}

