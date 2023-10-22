package com.diordna.miru.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.data.TodoRepositoryImpl
import com.diordna.miru.data.db.TodoDatabase
import com.diordna.miru.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val todoAdapter = TodoListAdapter()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTodoView()
        initViewModel()
        loadData()
    }

    private fun createGetMockupData(): ArrayList<TodoUiData> {
        val list = arrayListOf<TodoUiData>()
        repeat(10) {
            list.add(
                TodoUiData(
                    id = it.toLong(),
                    title = "Title $it",
                    isDone = false,
                    isMiru = false,
                    createAtMillis = 0,
                    updateAtMillis = 0
                )
            )
        }

        return list
    }

    private fun initTodoView() {
        todoAdapter.onClickDeleteAction = {
            homeViewModel.removeTodo(it)
        }

        binding?.todoListView?.adapter = todoAdapter
        binding?.todoListView?.layoutManager = LinearLayoutManager(activity)
        todoAdapter.submitList(createGetMockupData())

        binding?.addTodoButton?.setOnClickListener {
            homeViewModel.addNewTodo()
        }
    }

    private fun initViewModel() {
        activity?.let { acti ->
            val dataBase = Room.databaseBuilder(
                acti.applicationContext,
                TodoDatabase::class.java,
                "todo-database"
            ).build()

            homeViewModel.todoRepository = TodoRepositoryImpl(dataBase)
        }

        homeViewModel.todoList.observe(this) { list ->
            todoAdapter.submitList(list)
        }
    }

    private fun loadData() {
        homeViewModel.loadTodoList()
    }

}