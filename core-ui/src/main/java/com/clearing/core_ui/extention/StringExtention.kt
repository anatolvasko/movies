package com.clearing.core_ui.extention

import com.clearing.core_ui.Constants.EMPTY_STRING
import com.clearing.core_ui.Constants.INPUT_DATE_FORMAT
import com.clearing.core_ui.Constants.OUTPUT_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.Locale


fun String.toReadableDate(): String {
    return try {
        val inputFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault())

        val date = inputFormat.parse(this) ?: return EMPTY_STRING
        outputFormat.format(date)
    } catch (e: Exception) {
        EMPTY_STRING
    }
}