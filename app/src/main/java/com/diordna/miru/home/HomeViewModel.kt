package com.diordna.miru.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diordna.miru.data.TodoRepository
import com.diordna.miru.data.TodoUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _todoList = MutableLiveData<List<TodoUiData>>()
    val todoList: LiveData<List<TodoUiData>> = _todoList

    fun loadTodoList(dateTime: DateTime = DateTime.now()) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = todoRepository.getTodoForDate(dateTime)
            withContext(Dispatchers.Main) {
                _todoList.value = updatedList
            }
        }
    }

    fun addTodo(title: String, dateTime: DateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = todoRepository.addTodo(title, dateTime)

            _todoList.value?.toMutableList()?.run {
                add(todo)
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
            todoRepository.miruTodo(id)

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
            val todo = todoRepository.checkTodo(id, isChecked)

            val originList = _todoList.value?.toMutableList()
            val findItem = originList?.find { allList ->
                allList.id == id
            }

            findItem?.run {
                val originItemIndex = originList.indexOf(findItem)
                originList[originItemIndex] = todo
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
            todoRepository.removeTodo(id)

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