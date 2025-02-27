package com.example.shifumi.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
fun ShakeSensor(
    context: Context,
    onShakeDetected: () -> Unit
) {
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
    val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    DisposableEffect(sensorManager) {
        val sensorListener = object : SensorEventListener {
            private var lastShakeTime = 0L

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
                            Log.d("ShakeSensor", "Secousse détectée !")
                            onShakeDetected()
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
            Log.d("ShakeSensor", "Capteur désenregistré")
            sensorManager.unregisterListener(sensorListener)
        }
    }
}