package com.diordna.miru.home.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.diordna.miru.CalendarCalculator
import com.diordna.miru.data.Const
import com.diordna.miru.databinding.FragmentCalenerDateBinding
import org.joda.time.DateTime

class CalenderDateFragment : Fragment() {

    private var binding: FragmentCalenerDateBinding? = null

    companion object {

        private const val ARG_FRAGMENT_POSITION = "ARG_FRAGMENT_POSITION"

        fun getInstance(position: Int): Fragment {
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
        val dateTextViewArr = listOf(
            binding?.dateTextView0,
            binding?.dateTextView1,
            binding?.dateTextView2,
            binding?.dateTextView3,
            binding?.dateTextView4,
            binding?.dateTextView5,
            binding?.dateTextView6,
        )

        binding?.run {
            dateTextViewArr.forEachIndexed { index, textView ->
                textView?.text = CalendarCalculator.getDayText(plusWeeks, index)
            }
        }
    }
}