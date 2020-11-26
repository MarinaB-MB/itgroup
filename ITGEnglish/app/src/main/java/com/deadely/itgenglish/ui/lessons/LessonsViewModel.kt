package com.deadely.itgenglish.ui.lessons

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.deadely.itgenglish.base.BaseViewModel
import com.deadely.itgenglish.model.Ask
import com.deadely.itgenglish.model.Category
import com.deadely.itgenglish.model.Lessons
import com.deadely.itgenglish.repository.Repository
import dagger.hilt.android.qualifiers.ApplicationContext

class LessonsViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {

    private var mCategory = MutableLiveData<Category>()
    var category: LiveData<Category> = mCategory

    private val listOfCategories = listOf<Category>(
        Category(
            "0", listOf<Lessons>(
                Lessons(
                    "0", "Level 1", listOf<Ask>(
                        Ask("0", "Water"),
                        Ask("0", "Apple")
                    )
                ),
                Lessons(
                    "1", "Level 2", listOf<Ask>(
                        Ask("0", "Hello."),
                        Ask("0", "Hi."),
                    )
                ),
            )
        ),
        Category(
            "1", listOf<Lessons>(
                Lessons(
                    "0", "Level 1", listOf<Ask>(
                        Ask("0", "My name is Alice."),
                        Ask("0", "Nice to meet you.")
                    )
                ),
                Lessons(
                    "1", "Level 2", listOf<Ask>(
                        Ask("0", "What is your name?"),
                        Ask("0", "How are you?"),
                    )
                ),
            )
        ),
        Category(
            "1", listOf<Lessons>(
                Lessons(
                    "0", "Level 1", listOf<Ask>(
                        Ask("0", "The weather is nice outside."),
                        Ask("0", "It's time to learn your lessons.")
                    )
                ),
                Lessons(
                    "1", "Level 2", listOf<Ask>(
                        Ask("0", "You need to turn down the volume."),
                        Ask("0", "Do your homework."),
                    )
                ),
            )
        )
    )

    init {
        mCategory.postValue(listOfCategories[1])
    }

}
