package com.bonustrack02.numberbaseballgametp.game

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.Random

class GameViewModel : ViewModel() {
    private var num1: Int = 0
    private var num2: Int = 0
    private var num3: Int = 0

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
}