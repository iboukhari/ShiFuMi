package com.example.shifumi.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shifumi.R
import com.example.shifumi.navigation.Routes
import com.example.shifumi.utils.ShakeSensor
import com.example.shifumi.viewmodels.GameViewModel

@Composable
fun PlayScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = viewModel()
) {
    val context = LocalContext.current
    val gameState by gameViewModel.gameState.collectAsState()

    ShakeSensor(
        context = context,
        onShakeDetected = { gameViewModel.onShakeDetected() }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.marineford),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        gameState.selectedWeapon?.let { weapon ->
            Image(
                painter = painterResource(id = weapon),
                contentDescription = "Arme sélectionnée",
                modifier = Modifier
                    .height(800.dp)
                    .size(800.dp)
                    .animateContentSize()
            )
        }

        Button(
            onClick = { navController.navigate(Routes.HOME) },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 100.dp)
        ) {
            Text(
                text = "Page d'accueil",
                fontSize = 15.sp
            )
        }
    }
}