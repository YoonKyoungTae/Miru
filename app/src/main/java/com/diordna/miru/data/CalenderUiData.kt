package com.diordna.miru.data

import org.joda.time.DateTime

data class CalenderUiData(
    val dateTime: DateTime,
    val dayText: String = dateTime.toString("E"),
    val dateText: String = dateTime.toString("dd"),
)
