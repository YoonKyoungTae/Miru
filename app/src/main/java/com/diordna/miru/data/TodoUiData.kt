package com.diordna.miru.data

data class TodoUiData(
    val id: Long,
    val title: String,
    private val isDone: Boolean,
    private val isMiru: Boolean,
    private val createAtMillis: Long,
    private val updateAtMillis: Long
)
