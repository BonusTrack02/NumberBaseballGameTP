package com.bonustrack02.numberbaseballgametp.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Random

class GameViewModel : ViewModel() {
    private var num1 = 0
    private var num2 = 0
    private var num3 = 0
    private var tryCount = 0
    var stringBuilder = StringBuilder()
    private val _resultText = MutableLiveData<String>()
    val resultText: LiveData<String>
        get() = _resultText

    init {
        Log.d("gameViewModel", "created")
        generateRandomNumber()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("gameViewModel", "destroyed")
    }

    private fun generateRandomNumber() {
        val random = Random()
        num1 = random.nextInt(9) + 1
        do {
            num2 = random.nextInt(9) + 1
        } while (num1 == num2)
        do {
            num3 = random.nextInt(9) + 1
        } while (num1 == num3 || num2 == num3)
    }

    fun onSubmit(edit01: String, edit02: String, edit03: String) {
        Log.d("submit", edit01)
        val tempAnswer1: Int
        val tempAnswer2: Int
        val tempAnswer3: Int
        try {
            tempAnswer1 = edit01.toInt()
            tempAnswer2 = edit02.toInt()
            tempAnswer3 = edit03.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            // show toast
            return
        }
        var strikeCount = 0
        var ballCount = 0

        when (num1) {
            tempAnswer1 -> strikeCount++
            tempAnswer2 -> ballCount++
            tempAnswer3 -> ballCount++
        }

        when (num2) {
            tempAnswer2 -> strikeCount++
            tempAnswer1 -> ballCount++
            tempAnswer3 -> ballCount++
        }

        when (num3) {
            tempAnswer3 -> strikeCount++
            tempAnswer1 -> ballCount++
            tempAnswer2 -> ballCount++
        }

        tryCount++

        // append result text
        stringBuilder.append("시도 $tryCount : $tempAnswer1 $tempAnswer2 $tempAnswer3   ${strikeCount}Strike ${ballCount}Ball\n")
        _resultText.value = stringBuilder.toString()
        Log.d("result", stringBuilder.toString())

        if (strikeCount == 3) {
            // lottie image shown
            // keypad hide
        }

        // editText value erase

        // edit01 requestfocus
        // scrollView fullscroll
    }
}