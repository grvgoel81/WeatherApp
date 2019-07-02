package com.gaurav.gojekassignment.util

import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils(context: Context) {

    fun getDay(dateInString: String): String {
        val dateFormatterCurrent = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatterApplied = SimpleDateFormat("EEEE")
        var date: Date? = null
        try {
            date = dateFormatterCurrent.parse(dateInString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateFormatterApplied.format(date)
    }

}