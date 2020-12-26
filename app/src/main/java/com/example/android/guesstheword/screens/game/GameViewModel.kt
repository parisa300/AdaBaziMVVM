package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100,
        100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {


    companion object{
        private const val COUNT_TIME= 10000L
        private const val ONE_SECOND =1000L
        private const val DONE =0L
    }
    private val timer :CountDownTimer
    // The current word

 private val _word =MutableLiveData<String>()
    val word :LiveData<String>
    get() = _word
    // The current score
 private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

private val _current_time =MutableLiveData<Long>()
    val current_time :LiveData<Long>
    get() = _current_time

     val  currentTimeString=Transformations.map(current_time,{time ->
         DateUtils.formatElapsedTime(time)

     })

    private val _eventGameFinished =MutableLiveData<Boolean>()
    val eventGameFinished :LiveData<Boolean>
    get() = _eventGameFinished
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
    init {
        Log.i("game view model","game view model init!!")
        _score.value =0
        _eventGameFinished.value =false
        resetList()
        nextWord()

        timer =object :CountDownTimer(COUNT_TIME, ONE_SECOND){


            override fun onTick(millisUntilFinished: Long) {

                _current_time.value=millisUntilFinished/ ONE_SECOND
            }

            override fun onFinish() {
               _current_time.value= DONE
            }

        }
        timer.start()


    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("game view model","game view model destoroyd!!")
    }



    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }


    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
         //  gameFinished()
            _eventGameFinished.value=true
        } else {
            _word.value = wordList.removeAt(0)
        }

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value=score.value?.minus(1)
        nextWord()
    }

   fun onCorrect() {
       _score.value=score.value?.plus(1)
        nextWord()
    }


    fun onGameFinishedComplete(){
        _eventGameFinished.value=false
    }



}


enum class BuzzType(val pattern: LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}
