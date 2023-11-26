package com.diordna.miru

import androidx.lifecycle.MutableLiveData
import org.joda.time.DateTime
import org.joda.time.Days

object CalendarCalculator {

    private val CALENDER_START_DATE = DateTime()
        .withDate(2023, 1, 1)
        .millis

    var selectDayLiveData: MutableLiveData<Int>? = null

    fun getDateTime(plusWeeks: Int, plusDays: Int): DateTime {
        return DateTime(CALENDER_START_DATE)
            .plusWeeks(plusWeeks)
            .plusDays(plusDays)
    }

    fun getDayText(plusWeeks: Int, plusDays: Int): String {
        return DateTime(CALENDER_START_DATE)
            .plusWeeks(plusWeeks)
            .plusDays(plusDays)
            .dayOfMonth.toString()
    }

    fun getMonthText(plusWeeks: Int): String {
        return DateTime(CALENDER_START_DATE)
            .plusWeeks(plusWeeks)
            .monthOfYear.toString() + "월"
    }

    fun getMonthText(dateTime: DateTime): String {
        return dateTime.monthOfYear.toString() + "월"
    }

    fun getTodayWeeks() : Int {
        val betweenDays = Days.daysBetween(DateTime(CALENDER_START_DATE), DateTime.now()).days
        return betweenDays / 7
    }

}