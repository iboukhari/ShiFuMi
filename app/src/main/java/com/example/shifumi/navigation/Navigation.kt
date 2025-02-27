package com.example.shifumi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shifumi.screens.HomeScreen
import com.example.shifumi.screens.PlayScreen

object Routes {
    const val HOME = "home"
    const val PLAY = "play"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(Routes.PLAY) {
            PlayScreen(navController = navController)
        }
    }
}