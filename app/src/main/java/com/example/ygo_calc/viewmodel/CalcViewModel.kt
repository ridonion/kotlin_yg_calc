package com.example.ygo_calc.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalcViewModel : ViewModel() {
    var showModal = mutableStateOf(false)
    var player1LifePoints = mutableStateOf(8000)
    var player2LifePoints = mutableStateOf(8000)

    fun reset() {
        player1LifePoints.value = 8000
        player2LifePoints.value = 8000
        showModal.value = false
    }

    fun updateLifePoints(player: Int, value: Int, operation: ((Int, Int) -> Int)? = null) {
        when (player) {
            1 -> {
                if (operation == null) {
                    player1LifePoints.value = value
                } else {
                    player1LifePoints.value = operation(player1LifePoints.value, value ?: 0)
                }

                if (player1LifePoints.value <= 0) showModal.value = true
            }
            2 -> {
                if (operation == null) {
                    player2LifePoints.value = value
                } else {
                    player2LifePoints.value = operation(player2LifePoints.value, value ?: 0)
                }

                if (player2LifePoints.value <= 0) showModal.value = true
            }
        }
    }


    // 電卓の入力値
    var inputNumber = mutableStateOf("0")
    fun addInputNumber(value: Long?) {
        if (value == null) {
            return
        }

        var update = value.toInt();
        val inputMin = 0
        if (update <= inputMin) {
            update = 0
        }

        inputNumber.value = update.toString()
    }

    /**
     * ゲーム結果を保存
     */
    fun saveGameResult(context: Context) {
        val sharedPreferences = context.getSharedPreferences("game_results", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val result = "Player 1: ${player1LifePoints.value}, Player 2: ${player2LifePoints.value}, Winner: ${if (player1LifePoints.value > player2LifePoints.value) "Player 1" else "Player 2"}"
        editor.putString(System.currentTimeMillis().toString(), result)
        editor.apply()
    }
}