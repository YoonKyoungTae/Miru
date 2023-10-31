package com.diordna.miru.data

data class TodoUiData(
    val id: Long,
    val title: String,
    val isDone: Boolean,
    val isMiru: Boolean,
    val createAtMillis: Long,
    val viewingAtMillis: Long,
    val updateAtMillis: Long
)
