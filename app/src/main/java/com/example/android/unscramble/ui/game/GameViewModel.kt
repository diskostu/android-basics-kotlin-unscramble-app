package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private val wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created")

        getNextWord()
    }


    override fun onCleared() {
        super.onCleared()

        Log.d("GameFragment", "GameViewModel destroyed")
    }


    fun getNextWord() {
        // get a random word from the data store
        currentWord = allWordsList.random()
        // if we already used this word, get a new one
        if (wordsList.contains(currentWord)) getNextWord()

        // add the current word the the list of used words
        wordsList.add(currentWord)

        // shuffle the characters
        val tempWord = currentWord.toCharArray()
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        // set the new scrambled word, increase the counter
        _currentScrambledWord = String(tempWord)
        ++_currentWordCount
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }


    fun increaseScore() {
        _score += SCORE_INCREASE
    }


    fun isUserWordCorrect(playerWord: String): Boolean {
        return playerWord.equals(currentWord, true)
    }
}