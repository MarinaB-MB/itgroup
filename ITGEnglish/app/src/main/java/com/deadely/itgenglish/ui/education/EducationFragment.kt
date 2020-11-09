package com.deadely.itgenglish.ui.education

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.deadely.itgenglish.R
import com.deadely.itgenglish.extensions.setActivityTitle

class EducationFragment : Fragment(R.layout.fragment_education) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setActivityTitle(R.string.education)
    }
}
