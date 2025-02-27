package com.example.shifumi

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shifumi.ui.theme.ShiFuMiTheme

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiFuMiTheme {
                MaterialTheme{
                    AppNavigation()
                }
            }
        }
        startMusic()
    }

    private fun startMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ost)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("play") {
            PlayScreen(navController = navController)
        }
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ace),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )}
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Bienvenue dans notre jeu Shi Fu Mi",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color.White)
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { navController.navigate("play") },
            modifier = Modifier
                .height(100.dp)
                .padding(horizontal = 100.dp) ){
            Text(text = "Jouer",
                fontSize = 30.sp)
        }

    }
}

@Composable
fun PlayScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
    val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var shakeCount by remember { mutableStateOf(0) }
    var lastShakeTime by remember { mutableStateOf(0L) }
    var selectedWeapon by remember { mutableStateOf<Int?>(null) }




    DisposableEffect (sensorManager) {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]

                    val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble())
                    val shakeThreshold = 14
                    val currentTime = System.currentTimeMillis()

                    if (acceleration > shakeThreshold) {
                        if (currentTime - lastShakeTime > 400) {
                            lastShakeTime = currentTime
                            shakeCount++

                            Log.d("Sensor", "Secousse détectée ! Nombre : $shakeCount")

                            if (shakeCount >= 3) {
                                shakeCount = 0

                                
                                val weapons = listOf(R.drawable.luffy, R.drawable.garp, R.drawable.barbenoire)
                                selectedWeapon = weapons.random()

                                Log.d("Sensor", "Arme sélectionnée : $selectedWeapon")

                            }
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        accelerometer?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            Log.d("Sensor", "Capteur désenregistré")
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.marineford),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )}
    Column(
        modifier = Modifier.fillMaxSize().padding(50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        selectedWeapon?.let { weapon ->
            Image(
                painter = painterResource(id = weapon),
                contentDescription = "Arme sélectionnée",
                modifier = Modifier
                    .height(800.dp)
                    .size(800.dp)
                    .animateContentSize()
            )
        }

        Button(onClick = { navController.navigate("home") },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 100.dp) ){
            Text(text = "Page d'accueil", fontSize = 15.sp)
        }
    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShiFuMiTheme {
        Greeting("Android")
    }
}
