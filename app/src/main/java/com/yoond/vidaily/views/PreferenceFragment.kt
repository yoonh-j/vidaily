package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.vidaily.R

class PreferenceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preference, container, false)
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.layout_preference, PreferenceScreen())
            ?.commit()
        return root
    }
}