package com.diordna.miru

import com.diordna.miru.data.Const
import org.joda.time.DateTime

object CalendarCalculator {

    private val CALENDER_START_DATE = DateTime()
        .withDate(2023, 1, 1)
        .millis

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

}