package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(FinalScore :Int) :ViewModel() {

    private val _score=MutableLiveData<Int>()
    val score :LiveData<Int>
    get() = _score

    init {
      _score.value=FinalScore
    }
}