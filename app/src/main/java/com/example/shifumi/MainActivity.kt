package com.example.shifumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.shifumi.navigation.AppNavigation
import com.example.shifumi.ui.theme.ShiFuMiTheme
import com.example.shifumi.utils.MusicPlayer

class MainActivity : ComponentActivity() {
    private val musicPlayer = MusicPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiFuMiTheme {
                MaterialTheme {
                    AppNavigation()
                }
            }
        }
        musicPlayer.startMusic(this, R.raw.ost)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }
}