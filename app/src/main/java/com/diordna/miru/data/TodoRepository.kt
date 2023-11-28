package com.diordna.miru.data

import org.joda.time.DateTime

interface TodoRepository {

    fun getTodoForDate(dateTime: DateTime): List<TodoUiData>

    fun addTodo(title: String, dateTime: DateTime): TodoUiData

    fun miruTodo(id: Long)

    fun checkTodo(id: Long, isCheck: Boolean): TodoUiData

    fun removeTodo(id: Long)

}