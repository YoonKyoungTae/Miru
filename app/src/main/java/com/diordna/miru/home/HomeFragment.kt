package com.diordna.miru.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.diordna.miru.data.CalenderUiData
import com.diordna.miru.data.TodoRepositoryImpl
import com.diordna.miru.data.db.TodoDatabase
import com.diordna.miru.databinding.FragmentHomeBinding
import com.diordna.miru.home.calender.CalenderPagerAdapter
import org.joda.time.DateTime

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
        initCalenderView()

        initViewModel()
        loadData()
    }

    private fun initCalenderView() {

        val mockData = arrayListOf<CalenderUiData>()
        repeat(30) {
            mockData.add(CalenderUiData(DateTime()))
        }

        val calenderAdapter = CalenderPagerAdapter(this)
        binding?.calenderPagerView?.adapter = calenderAdapter
    }

    private fun initTodoView() {
        binding?.todoListView?.adapter = todoAdapter
        binding?.todoListView?.layoutManager = LinearLayoutManager(activity)

        // Add
        binding?.addTodoButton?.setOnClickListener {
            homeViewModel.addNewTodo()
        }

        // Delete
        todoAdapter.onClickDeleteAction = {
            homeViewModel.removeTodo(it)
        }

        // Edit
        todoAdapter.onClickIsDoneAction = { id, isChecked ->
            homeViewModel.editTodo(id, isChecked)
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