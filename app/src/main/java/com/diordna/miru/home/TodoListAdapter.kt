package com.diordna.miru.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diordna.miru.data.TodoUiData
import com.diordna.miru.databinding.ItemTodoBinding
import org.joda.time.DateTime

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

    var onClickDeleteAction: ((id: Long) -> Unit)? = null
    var onClickIsDoneAction: ((id: Long, isChecked: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(
            binding,
            onClickDeleteAction,
            onClickIsDoneAction
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    fun getItemFromPosition(position: Int): TodoUiData {
        return getItem(position)
    }

    class ViewHolder(
        private val binding: ItemTodoBinding,
        private val onClickDeleteAction: ((id: Long) -> Unit)?,
        private val onClickIsDoneAction: ((id: Long, isChecked: Boolean) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binding(todo: TodoUiData) {
            binding.todoTitle.text = todo.title
            binding.todoCreateAt.text = DateTime(todo.createAtMillis).toString("yyyy-MM-dd")
            handleCheckBoxAction(todo.isDone)

            binding.todoDeleteIcon.setOnClickListener {
                onClickDeleteAction?.invoke(todo.id)
            }

            binding.todoCheckBox.setOnClickListener {
                handleCheckBoxAction(binding.todoCheckBox.isChecked)
                onClickIsDoneAction?.invoke(todo.id, binding.todoCheckBox.isChecked)
            }
        }

        private fun handleCheckBoxAction(isDone: Boolean) {
            if (isDone) {
                binding.todoTitle.paintFlags = binding.todoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.todoTitle.paintFlags = binding.todoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            binding.todoCheckBox.isChecked = isDone
        }

    }

}