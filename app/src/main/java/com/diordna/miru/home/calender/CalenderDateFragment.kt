package com.diordna.miru.home.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.diordna.miru.CalendarCalculator
import com.diordna.miru.custom.DaySelectView
import com.diordna.miru.databinding.FragmentCalenerDateBinding
import org.joda.time.DateTime

class CalenderDateFragment : Fragment() {

    private var binding: FragmentCalenerDateBinding? = null
    private val daySelectViews: ArrayList<DaySelectView> = arrayListOf()
    var onClickDayItem: OnClickDayItem? = null

    companion object {

        private const val ARG_FRAGMENT_POSITION = "ARG_FRAGMENT_POSITION"

        fun getInstance(position: Int): CalenderDateFragment {
            return CalenderDateFragment().apply {
                arguments = bundleOf(ARG_FRAGMENT_POSITION to position)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalenerDateBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDate()
    }

    private fun setDate() {
        val plusWeeks = arguments?.getInt(ARG_FRAGMENT_POSITION, 0) ?: 0
        binding?.run {
            daySelectViews.add(dateTextView0)
            daySelectViews.add(dateTextView1)
            daySelectViews.add(dateTextView2)
            daySelectViews.add(dateTextView3)
            daySelectViews.add(dateTextView4)
            daySelectViews.add(dateTextView5)
            daySelectViews.add(dateTextView6)
        }

        binding?.run {
            daySelectViews.forEachIndexed { index, daySelectView ->
                val setDate = CalendarCalculator.getDateTime(plusWeeks, index)
                daySelectView.setDate(setDate)
                daySelectView.setText(CalendarCalculator.getDayText(plusWeeks, index))
                daySelectView.setOnClickListener { selectView ->
                    onClickDayItem?.onClick(setDate)
                    refreshSelectDateView(selectView)
                }
            }
        }
    }

    private fun refreshSelectDateView(selectedView: View) {
        daySelectViews.forEach { it.isSelected(false) }
        daySelectViews.find {
            selectedView.id == it.id
        }?.isSelected(true)
    }

    interface OnClickDayItem {
        fun onClick(date: DateTime)
    }
}