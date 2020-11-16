package com.deadely.itgenglish.ui.dictionary

import android.os.Build
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.itgenglish.R
import com.deadely.itgenglish.base.BaseFragment
import com.deadely.itgenglish.extensions.setActivityTitle
import com.deadely.itgenglish.model.Word
import com.deadely.itgenglish.utils.CustomAdapter
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.row_word.view.*
import java.util.*

class DictionaryFragment : BaseFragment(R.layout.fragment_dictionary) {

    private var ttsEnabled: Boolean = false
    private lateinit var tts: TextToSpeech
    private val list: List<Word> = listOf(
        Word("0", "apple", "[yabloko]", "яблоко"),
        Word("1", "word", "[voed]", "слово"),
        Word("2", "you", "[ti]", "ты"),
    )

    private val wordsAdapter = CustomAdapter<Word>(R.layout.row_word) { viewHolder, word, _, _ ->
        with(viewHolder.itemView) {
            tvWord.text = word.word
            tvDescription.text = word.translate
            tvTr.text = word.tr
            ivVolume.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sayWordGreater21(word.word)
                } else {
                    sayWordUnder20(word.word)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun sayWordGreater21(word: String) {
        val utteranceId = this.hashCode().toString() + ""
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    private fun sayWordUnder20(word: String) {
        val map: HashMap<String, String> = HashMap()
        map[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "MessageId"
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, map)
    }

    private fun initTts() {
        tts = TextToSpeech(context) { initStatus ->
            if (initStatus == TextToSpeech.SUCCESS) {
                tts.language = Locale.ENGLISH
                tts.setPitch(1.3f)
                tts.setSpeechRate(1f)
                ttsEnabled = true
            } else {
                Toast.makeText(context, getString(R.string.happened_error), Toast.LENGTH_SHORT)
                    .show()
                ttsEnabled = false
            }
        }
    }

    override fun initView() {
        initTts()
        setActivityTitle(R.string.dictionary)
        rvWords.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordsAdapter
        }
        wordsAdapter.setData(list)
    }

    override fun setListeners() {}

    override fun initObserver() {}

    override fun getExtras() {}
}
