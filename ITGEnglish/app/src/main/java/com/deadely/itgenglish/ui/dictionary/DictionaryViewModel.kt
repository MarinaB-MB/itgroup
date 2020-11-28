package com.deadely.itgenglish.ui.dictionary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deadely.itgenglish.model.Word
import com.deadely.itgenglish.repository.Repository

class DictionaryViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private var mListWords = MutableLiveData<List<Word>>()
    var listWords: LiveData<List<Word>> = mListWords

    private val staticList = mutableListOf(
        Word("0", "apple", "[yabloko]", "яблоко", false),
        Word("1", "word", "[vord]", "слово", false),
        Word("2", "you", "[ti]", "ты", false),
        Word("0", "apple", "[yabloko]", "яблоко", false),
        Word("1", "word", "[vord]", "слово", false),
        Word("2", "you", "[ti]", "ты", false),
        Word("0", "apple", "[yabloko]", "яблоко", false),
        Word("1", "word", "[vord]", "слово", false),
        Word("2", "you", "[ti]", "ты", false)
    )

    init {
        getListWord()
    }

    private fun getListWord() {
        mListWords.postValue(staticList)
    }

    fun onSearchTextChanged(text: String) {
        var filteredList = listOf<Word>()
        if (text.isNotEmpty()) {
            filteredList = staticList.filter {
                it.word.contains(text, ignoreCase = true)
            }
            mListWords.postValue(filteredList)
        } else {
            mListWords.postValue(staticList)
        }
    }

    fun deleteFavorites(word: Word) {
        repository.deleteFromFavoriteList(word)
    }

    fun addFavorite(word: Word) {
        repository.addToFavoriteList(word)
    }
}
