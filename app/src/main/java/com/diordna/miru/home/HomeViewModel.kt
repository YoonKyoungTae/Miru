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
                    }
                }
            }
        }
    }

    fun addNewTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                val newTodoItem = TodoEntity(
                    createAtMillis = DateTime.now().millis,
                    updateAtMillis = DateTime.now().millis
                )
                it.todoDatabase.todoDao().insert(newTodoItem)

                _todoList.value?.toMutableList()?.run {
                    add(newTodoItem.toUiData())
                    withContext(Dispatchers.Main) {
                        _todoList.value = this@run
                    }
                }
            }
        }
    }

    fun editTodo() {

    }

    fun removeTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository?.let {
                it.todoDatabase.todoDao().deleteForId(id)

                val originList = _todoList.value?.toMutableList()
                val filterList = originList?.filter { allList ->
                    allList.id == id
                }

                if (filterList?.isNotEmpty() == true) {
                    originList.remove(filterList[0])
                    withContext(Dispatchers.Main) {
                        _todoList.value = originList!!
                    }
                }
            }
        }
    }
}