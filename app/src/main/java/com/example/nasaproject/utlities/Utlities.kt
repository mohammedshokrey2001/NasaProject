package com.example.nasaproject.utlities

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(days:Int = 0):String{

    val calendar = Calendar.getInstance()
    if (days>0){
        calendar.add(Calendar.DAY_OF_YEAR,days)
    }
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}