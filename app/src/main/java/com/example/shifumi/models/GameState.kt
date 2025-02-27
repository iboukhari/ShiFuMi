package com.example.shifumi.models

data class GameState(
    val shakeCount: Int = 0,
    val lastShakeTime: Long = 0,
    val selectedWeapon: Int? = null
)