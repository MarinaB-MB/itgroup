package com.deadely.itgenglish.ui.lessons

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseFragment
import com.deadely.itgenglish.extensions.setActivityTitle
import com.deadely.itgenglish.model.Lessons
import com.deadely.itgenglish.ui.education.EducationActivity
import com.deadely.itgenglish.utils.LESSON
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lessons.*


@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons) {

    private val viewModel: LessonsViewModel by viewModels()
    private val lessonsAdapter = LessonsAdapter()

    override fun initView() {
        setActivityTitle(R.string.lessons)
        rvLessons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonsAdapter
        }

    }

    private fun openEducationScreen(lessons: Lessons) {
        val intent = Intent(activity, EducationActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(LESSON, lessons)
        }
        intent.putExtra(LESSON, bundle)
        startActivity(intent)
    }

    override fun setListeners() {
        lessonsAdapter.setOnClickListener(object : LessonsAdapter.OnItemClickListener {
            override fun onClick(lessons: Lessons) {
                openEducationScreen(lessons)
            }
        })
    }

    override fun initObserver() {
        viewModel.category.observe(viewLifecycleOwner, {
            it.lessons?.let { lessons -> lessonsAdapter.setData(lessons) }
        })
    }

    override fun getExtras() {}

}