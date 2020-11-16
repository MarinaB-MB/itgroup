package com.deadely.itgenglish.ui.education

import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseFragment
import com.deadely.itgenglish.extensions.setActivityTitle

class EducationFragment : BaseFragment(R.layout.fragment_education) {

    override fun initView() {
        setActivityTitle(R.string.education)
    }

    override fun setListeners() {}

    override fun initObserver() {}

    override fun getExtras() {}
}
