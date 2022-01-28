package com.stormstreet.myapplication.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import java.util.concurrent.TimeUnit

class DateFormat {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun covertTimeOtherFormat(dataDate: String?): String? {
            var convTime = ""
            val suffix = "ago."
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                sdf.timeZone = TimeZone.getTimeZone("UTC")

                //String time = getUtcTime(dataDate.substring(0,19));
                //  String[] timeie=dataDate.split("\\.");
                val date = sdf.parse(dataDate)
                val nowTime = Date()
                val dateDiff = nowTime.time - date.time
                try {
                    val newSdf = SimpleDateFormat("yyyy-mm-dd")
                    newSdf.timeZone = TimeZone.getTimeZone("UTC")
                    var startTime = newSdf.format(date)
                    var endTime = newSdf.format(nowTime)
                    val localeDateStart = LocalDate.parse(startTime)
                    val localeDateEnd = LocalDate.parse(endTime)


                var period = Period.between(localeDateStart,localeDateEnd)

                var month = period.months
                var year = period.years
                }catch (e: Exception) {
                    e.printStackTrace()
                }
//                var days = period.days
//                val weekOfMonth = calendar[Calendar.WEEK_OF_MONTH]
                val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
                val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
                val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
                val days = (nowTime.time - date.time)/(24*60*60*1000)

                if (second < 60) {
                    convTime = "Just Now"
                } else if (minute < 60) {
                    convTime = "$minute mins $suffix"
                } else if (hour < 24) {
                    convTime = "$hour hours $suffix"
                } else if (hour >= 24) {
                    convTime = "$days day $suffix"
                }
//                else if(days > 30) {
//                    convTime = "$month month $suffix"
//                } else if(month > 12) {
//                    convTime = "$year year $suffix"
//                }
            } catch (e: ParseException) {
                e.printStackTrace()
                //Log.e("ConvTimeE", e.getMessage());
            }
            return convTime
        }



        fun getDateOfhourminute(date: String?): String? {
            var result = ""

//        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSS'Z'");
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            //  SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("h:mm aa")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }
    }
}