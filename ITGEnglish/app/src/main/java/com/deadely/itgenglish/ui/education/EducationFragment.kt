package com.deadely.itgenglish.ui.education

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.view.MotionEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseFragment
import com.deadely.itgenglish.extensions.setActivityTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_education.*
import java.util.*
import kotlin.concurrent.timerTask


@AndroidEntryPoint
class EducationFragment : BaseFragment(R.layout.fragment_education) {

    private val viewModel: EducationViewModel by viewModels()

    override fun initView() {
        setActivityTitle(R.string.education)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {
        var timer: Timer? = Timer()
        var isRecording: Boolean? = null
        ivPlay.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    timer = Timer().apply {
                        schedule(timerTask {
                            isRecording = true
                            startRecorder()
                            ivPlay.setImageResource(R.drawable.ic_baseline_mic_on)
                        }, 1000)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (isRecording == true) {
                        isRecording = false
                        timer?.apply {
                            cancel()
                            purge()
                        }
                        stopRecorder()
                        ivPlay.setImageResource(R.drawable.ic_baseline_mic_off_24)
                    }
                }
            }
            true
        }
    }

    private fun startRecorder() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    permissions,
                    0
                )
            }
        } else {
            viewModel.startRecord()
        }
    }

    private fun stopRecorder() {
        viewModel.stopRecord()
    }

    override fun initObserver() {

    }

    override fun getExtras() {}
}
