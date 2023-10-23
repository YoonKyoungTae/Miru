package com.diordna.miru.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diordna.miru.data.TodoRepository
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.data.db.TodoEntity
import com.diordna.miru.data.db.toUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class HomeViewModel : ViewModel() {

    var todoRepository: TodoRepository? = null
    private val _todoList = MutableLiveData<List<TodoUiData>>()
    val todoList: LiveData<List<TodoUiData>> = _todoList

    fun loadTodoList() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                val todoDataList = it.todoDatabase.todoDao().selectAll()

                withContext(Dispatchers.Main) {
                    _todoList.value = todoDataList.map { todoEntity ->
                        todoEntity.toUiData()
                    }.sortedBy { todoUiData ->
                        todoUiData.isDone
                    }
                }
            }
        }
    }

    fun addNewTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                val newTodoItem = TodoEntity(
                    createAtMillis = DateTime().millis,
                    updateAtMillis = DateTime().millis
                )
                val newTodoItemId = it.todoDatabase.todoDao().insert(newTodoItem)
                newTodoItem.id = newTodoItemId

                _todoList.value?.toMutableList()?.run {
                    add(newTodoItem.toUiData())
                    withContext(Dispatchers.Main) {
                        _todoList.value = this@run.sortedBy { todoUiData ->
                            todoUiData.isDone
                        }
                    }
                }
            }
        }
    }

    fun editTodo(id: Long, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                val todoEntity = it.todoDatabase.todoDao().selectForId(id)
                todoEntity.isDone = isChecked
                todoEntity.updateAtMillis = DateTime().millis
                it.todoDatabase.todoDao().update(todoEntity)

                val originList = _todoList.value?.toMutableList()
                val findItem = originList?.find { allList ->
                    allList.id == id
                }

                findItem?.run {
                    val originItemIndex = originList.indexOf(findItem)
                    originList[originItemIndex] = todoEntity.toUiData()
                    withContext(Dispatchers.Main) {
                        _todoList.value = originList.sortedBy { todoUiData ->
                            todoUiData.isDone
                        }
                    }
                }
            }
        }
    }

    fun removeTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                it.todoDatabase.todoDao().deleteForId(id)

                val originList = _todoList.value?.toMutableList()
                val findItem = originList?.find { allList ->
                    allList.id == id
                }

                findItem?.run {
                    originList.remove(this)
                    withContext(Dispatchers.Main) {
                        _todoList.value = originList!!
                    }
                }
            }
        }
    }
}