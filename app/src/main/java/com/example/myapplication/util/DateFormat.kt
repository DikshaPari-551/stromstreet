package com.example.myapplication.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateFormat {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun covertTimeOtherFormat(dataDate: String?): String? {
            var convTime = ""
            val suffix = "ago"
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                sdf.timeZone = TimeZone.getTimeZone("UTC")

                //String time = getUtcTime(dataDate.substring(0,19));
                //  String[] timeie=dataDate.split("\\.");
                val date = sdf.parse(dataDate)
                val calendar: Calendar = GregorianCalendar(date.year, date.month, date.day, date.hours, date.minutes, date.seconds)
                val nowTime = Date()
                val dateDiff = nowTime.time - date.time
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
                val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
                val weekOfYear = calendar[Calendar.WEEK_OF_YEAR]
                val weekOfMonth = calendar[Calendar.WEEK_OF_MONTH]
                val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
                val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
                val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
                if (second < 60) {
                    convTime = "Just Now"
                } else if (minute < 60) {
                    convTime = "$minute mins $suffix"
                } else if (hour < 24) {
                    convTime = "$hour hours $suffix"
                } else if (hour >= 24) {
                    val newSdf = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
                    newSdf.timeZone = TimeZone.getTimeZone("UTC")
//                    convTime = newSdf.format(date)
                    convTime = "$dayOfMonth days $suffix"

                }
            } catch (e: ParseException) {
                e.printStackTrace()
                //Log.e("ConvTimeE", e.getMessage());
            }
            return convTime
        }
    }
}