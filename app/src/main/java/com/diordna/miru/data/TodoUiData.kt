package com.diordna.miru.data

data class TodoUiData(
    val id: Long,
    val title: String,
    val isDone: Boolean,
    val viewingDate: String,
    val createAtMillis: Long,
    val updateAtMillis: Long
)
