package com.diordna.miru.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.databinding.ItemTodoBinding

class TodoListAdapter : ListAdapter<TodoUiData, TodoListAdapter.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoUiData>() {
            override fun areItemsTheSame(oldItem: TodoUiData, newItem: TodoUiData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TodoUiData, newItem: TodoUiData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binding(todo: TodoUiData) {
            binding.todoTitle.text = todo.title
        }

    }

}