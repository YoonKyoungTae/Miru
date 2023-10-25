package com.diordna.miru.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diordna.miru.data.CalenderUiData
import com.diordna.miru.databinding.ItemCalenderDayBinding

class CalenderListAdapter : ListAdapter<CalenderUiData, CalenderListAdapter.ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CalenderUiData>() {
            override fun areItemsTheSame(oldItem: CalenderUiData, newItem: CalenderUiData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CalenderUiData, newItem: CalenderUiData): Boolean {
                return oldItem.dateTime.millis == newItem.dateTime.millis
            }
        }
    }

    var onClickDeleteAction: ((id: Long) -> Unit)? = null
    var onClickIsDoneAction: ((id: Long, isChecked: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalenderDayBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(
            binding,
            onClickDeleteAction,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemCalenderDayBinding,
        private val onClickDeleteAction: ((id: Long) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binding(dayData: CalenderUiData) {
            binding.dayTextView.text = dayData.dayText
            binding.dateTextView.text = dayData.dateText
        }

    }

}