package com.diordna.miru.data

import com.diordna.miru.data.db.TodoDatabase
import com.diordna.miru.data.db.TodoEntity
import com.diordna.miru.data.db.toUiData
import org.joda.time.DateTime
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDatabase: TodoDatabase
) : TodoRepository {

    override fun getTodoForDate(dateTime: DateTime): List<TodoUiData> {
        val todoDataList = todoDatabase.todoDao().selectForDate(dateTime.toString("yyyyMMdd"))
        return todoDataList.map { todoEntity ->
            todoEntity.toUiData()
        }.sortedBy { todoUiData ->
            todoUiData.isDone
        }
    }

    override fun addTodo(title: String, dateTime: DateTime): TodoUiData {
        val newTodoItem = TodoEntity(
            title = title,
            viewingDate = dateTime.toString("yyyyMMdd"),
            createAtMillis = dateTime.millis,
            updateAtMillis = dateTime.millis
        )
        val newTodoItemId = todoDatabase.todoDao().insert(newTodoItem)
        newTodoItem.id = newTodoItemId
        return newTodoItem.toUiData()
    }

    override fun miruTodo(id: Long) {
        val todoEntity = todoDatabase.todoDao().selectForId(id)
        todoEntity.isMiru = true
        todoEntity.updateAtMillis = DateTime.now().millis
        todoDatabase.todoDao().update(todoEntity)
    }

    override fun checkTodo(id: Long, isCheck: Boolean): TodoUiData {
        val todoEntity = todoDatabase.todoDao().selectForId(id)
        todoEntity.isDone = isCheck
        todoEntity.updateAtMillis = DateTime.now().millis
        todoDatabase.todoDao().update(todoEntity)
        return todoEntity.toUiData()
    }

    override fun removeTodo(id: Long) {
        todoDatabase.todoDao().deleteForId(id)
    }

}