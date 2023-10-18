package com.diordna.miru.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.data.TodoRepository
import com.diordna.miru.data.db.toUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                    }
                }
            }
        }
    }
}