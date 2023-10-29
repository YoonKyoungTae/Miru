package com.diordna.miru.home.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.diordna.miru.data.Const
import com.diordna.miru.databinding.FragmentCalenerDateBinding
import com.diordna.miru.databinding.FragmentHomeBinding
import org.joda.time.DateTime

class CalenderDateFragment : Fragment() {

    private var binding: FragmentCalenerDateBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalenerDateBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDate()
    }

    private fun setDate() {

        binding?.run {
            dateTextView0.text = DateTime(Const.CALENDER_START_DATE).dayOfMonth.toString()
            dateTextView1.text = DateTime(Const.CALENDER_START_DATE).plusDays(1).dayOfMonth.toString()
            dateTextView2.text = DateTime(Const.CALENDER_START_DATE).plusDays(2).dayOfMonth.toString()
            dateTextView3.text = DateTime(Const.CALENDER_START_DATE).plusDays(3).dayOfMonth.toString()
            dateTextView4.text = DateTime(Const.CALENDER_START_DATE).plusDays(4).dayOfMonth.toString()
            dateTextView5.text = DateTime(Const.CALENDER_START_DATE).plusDays(5).weekyear.toString()
            dateTextView6.text = DateTime(Const.CALENDER_START_DATE).plusDays(6).weekOfWeekyear.toString()
        }
    }
}