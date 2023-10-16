package com.diordna.miru.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diordna.miru.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.mainTextView?.text = "it's cool!"
    }

}