package com.diordna.miru.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diordna.miru.data.TodoRepositoryImpl
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.data.db.TodoEntity
import com.diordna.miru.data.db.toUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepositoryImpl
) : ViewModel() {

    private val _todoList = MutableLiveData<List<TodoUiData>>()
    val todoList: LiveData<List<TodoUiData>> = _todoList

    fun loadTodoList(dateTime: DateTime = DateTime.now()) {
        viewModelScope.launch(Dispatchers.IO) {
            val todoDataList = todoRepository.todoDatabase.todoDao().selectForDate(dateTime.toString("yyyyMMdd"))

            withContext(Dispatchers.Main) {
                _todoList.value = todoDataList.map { todoEntity ->
                    todoEntity.toUiData()
                }.sortedBy { todoUiData ->
                    todoUiData.isDone
                }
            }
        }
    }

    fun addTodo(title: String, dateTime: DateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodoItem = TodoEntity(
                title = title,
                viewingDate = dateTime.toString("yyyyMMdd"),
                createAtMillis = dateTime.millis,
                updateAtMillis = dateTime.millis
            )
            val newTodoItemId = todoRepository.todoDatabase.todoDao().insert(newTodoItem)
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

    fun miruTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val todoEntity = todoRepository.todoDatabase.todoDao().selectForId(id)
                todoEntity.isMiru = true
                todoEntity.updateAtMillis = DateTime.now().millis
                todoRepository.todoDatabase.todoDao().update(todoEntity)

                val originList = _todoList.value?.toMutableList()
                originList?.find { allList ->
                    allList.id == id
                }?.run {
                    originList.remove(this)
                    withContext(Dispatchers.Main) {
                        _todoList.value = originList!!
                    }
                }
        }
    }

    fun editTodo(id: Long, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val todoEntity = todoRepository.todoDatabase.todoDao().selectForId(id)
            todoEntity.isDone = isChecked
            todoEntity.updateAtMillis = DateTime.now().millis
            todoRepository.todoDatabase.todoDao().update(todoEntity)

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

    fun removeTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.todoDatabase.todoDao().deleteForId(id)

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