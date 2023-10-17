package com.diordna.miru.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diordna.miru.data.Todo
import com.diordna.miru.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val todoAdapter = TodoListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTodoListView()
    }

    private fun createGetMockupData(): ArrayList<Todo> {
        val list = arrayListOf<Todo>()
        repeat(10) {
            list.add(
                Todo(
                    "Title ${it}",
                    false,
                    false,
                    0,
                    0
                )
            )
        }

        return list
    }

    private fun initTodoListView() {
        binding?.todoListView?.adapter = todoAdapter
        binding?.todoListView?.layoutManager = LinearLayoutManager(activity)
        todoAdapter.submitList(createGetMockupData())
    }

}