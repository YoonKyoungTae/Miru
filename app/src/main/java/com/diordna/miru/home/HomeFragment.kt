package com.diordna.miru.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.diordna.miru.CalendarCalculator
import com.diordna.miru.custom.ItemTouchHelperImpl
import com.diordna.miru.databinding.FragmentHomeBinding
import com.diordna.miru.home.calender.CalenderDateFragment
import com.diordna.miru.home.calender.CalenderPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val todoAdapter = TodoListAdapter()
    private val homeViewModel: HomeViewModel by viewModels()
    private var currentSelectDate: DateTime = DateTime.now()

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
        val calenderDayClickListener = object : CalenderDateFragment.OnClickDayItem {
            override fun onClick(date: DateTime) {
                currentSelectDate = date
                binding?.dayTextLayout?.monthTextView?.text = CalendarCalculator.getMonthText(date)
                homeViewModel.loadTodoList(date)
            }
        }

        val calenderAdapter = CalenderPagerAdapter(this, calenderDayClickListener)

        binding?.calenderPagerView?.adapter = calenderAdapter
        binding?.calenderPagerView?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding?.dayTextLayout?.monthTextView?.text = CalendarCalculator.getMonthText(position)
            }
        })

        // move to today
        binding?.calenderPagerView?.setCurrentItem(CalendarCalculator.getTodayWeeks(), false)
        calenderAdapter.moveToToday()
        CalendarCalculator.selectDayLiveData?.value = 4
    }

    private fun initTodoView() {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperImpl(object : ItemTouchHelperImpl.OnItemTouchListener {
            override fun onDragItem(fromPosition: Int, toPosition: Int): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwipeItem(position: Int) {
                val todoItem = todoAdapter.getItemFromPosition(position)
                homeViewModel.miruTodo(todoItem.id)
            }
        }))
        itemTouchHelper.attachToRecyclerView(binding?.todoListView)

        binding?.todoListView?.adapter = todoAdapter
        binding?.todoListView?.layoutManager = LinearLayoutManager(activity)

        // Add
        binding?.addTodoButton?.setOnClickListener {
            homeViewModel.addTodo(
                binding?.addEditText?.text.toString(),
                currentSelectDate)

            binding?.addEditText?.text = null
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
        homeViewModel.todoList.observe(this) { list ->
            todoAdapter.submitList(list)
        }
    }

    private fun loadData() {
        homeViewModel.loadTodoList()
    }

}