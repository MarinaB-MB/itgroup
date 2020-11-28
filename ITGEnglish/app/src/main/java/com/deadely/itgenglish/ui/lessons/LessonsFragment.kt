package com.deadely.itgenglish.ui.lessons

import androidx.fragment.app.viewModels
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lessons.*


@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons) {

    private val viewModel: LessonsViewModel by viewModels()


    override fun initView() {
        activity?.title = getString(R.string.lessons)
        viewPager.adapter = ViewPagerAdapter(fragmentManager)
        viewPager.currentItem = 0
    }

    override fun setListeners() {
    }

    override fun initObserver() {

    }

    override fun getExtras() {}

}
