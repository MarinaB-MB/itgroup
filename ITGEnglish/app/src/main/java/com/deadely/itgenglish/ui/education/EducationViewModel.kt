package com.deadely.itgenglish.ui.education

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.deadely.itgenglish.repository.Repository
import com.deadely.itgenglish.utils.DataState
import com.deadely.itgenglish.utils.POST_AUDIO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

class EducationViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private var isRecording: Boolean = false

    private var mValid = MutableLiveData<DataState<Boolean>>()
    var isValid: LiveData<DataState<Boolean>> = mValid

    private var recorder: MediaRecorder? = null
    private val fileName = "${Environment.getExternalStorageDirectory().absolutePath}/output.3gpp"

    fun startRecord() {
        if (!isRecording) {
            releaseRecorder()
            val outFile = File(fileName)
            if (outFile.exists()) {
                outFile.delete()
            }
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setMaxDuration(10000)
                setOutputFile(fileName)
                prepare()
                start()
            }
            isRecording = true
        } else {
            stopRecord()
        }
    }

    private fun releaseRecorder() {
        recorder?.release()
        recorder = null
    }

    fun stopRecord() {
        if (isRecording) {
            recorder?.apply {
                stop()
            }
            isRecording = false
        }
    }

    fun sendAudio() {
        val audioFile = File(fileName)
        viewModelScope.launch {
            repository.sendAudio(audioFile)
                .onEach { dataState -> subscribeData(dataState, POST_AUDIO) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribeData(dataState: DataState<Any>, code: String) {
        when (dataState) {
            is DataState.Loading -> {
                when (code) {
                    POST_AUDIO -> {
                        mValid.postValue(DataState.Loading)
                    }
                }
            }
            is DataState.Error -> {
                when (code) {
                    POST_AUDIO -> {
                        mValid.postValue(DataState.Error(dataState.exception))
                    }
                }

            }
            is DataState.Success -> {
                when (code) {
                    POST_AUDIO -> {
                        mValid.postValue(DataState.Success(dataState.data as Boolean))
                    }
                }
            }
        }
    }
}
