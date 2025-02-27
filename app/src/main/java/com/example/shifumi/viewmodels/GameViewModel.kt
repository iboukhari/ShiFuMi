package com.example.shifumi.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shifumi.R
import com.example.shifumi.models.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val weapons = listOf(R.drawable.luffy, R.drawable.garp, R.drawable.barbenoire)

    fun onShakeDetected() {
        _gameState.update { currentState ->
            val currentTime = System.currentTimeMillis()
            val newShakeCount = currentState.shakeCount + 1

            Log.d("GameViewModel", "Secousse détectée ! Nombre : $newShakeCount")

            if (newShakeCount >= 3) {
                val selectedWeapon = weapons.random()
                Log.d("GameViewModel", "Arme sélectionnée : $selectedWeapon")

                currentState.copy(
                    shakeCount = 0,
                    lastShakeTime = currentTime,
                    selectedWeapon = selectedWeapon
                )
            } else {
                currentState.copy(
                    shakeCount = newShakeCount,
                    lastShakeTime = currentTime
                )
            }
        }
    }
}