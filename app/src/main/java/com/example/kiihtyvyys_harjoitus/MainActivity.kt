package com.example.kiihtyvyys_harjoitus

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kiihtyvyys_harjoitus.ui.theme.KiihtyvyysharjoitusTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    var xCoord by mutableStateOf(1f)
    var yCoord by mutableStateOf(1f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSensor()
        setContent {
            KiihtyvyysharjoitusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BallGame(xCoord = xCoord, yCoord = yCoord)
                }
            }
        }
    }
    fun setUpSensor(){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onSensorChanged(event: SensorEvent?) {
        val xMax = windowManager.currentWindowMetrics.bounds.width()
        val yMax = windowManager.currentWindowMetrics.bounds.height()
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){

                xCoord -= event.values[0]*10

            if (xCoord<100f){
                xCoord = 100f
            }else if (xCoord> xMax-100f){
                xCoord = xMax -100f
            }

                yCoord += event.values[1]*10

            if (yCoord<100f){
                yCoord = 100f
            }else if (yCoord> yMax-100f){
                yCoord = yMax -100f
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}

@Composable
fun BallGame(modifier: Modifier = Modifier, xCoord: Float, yCoord: Float) {
    Canvas(modifier = Modifier.fillMaxSize()){
        drawCircle(
            color = Color.Red,
            center = Offset(x = xCoord, y = yCoord),
            radius = 100f
        )
    }
}

@Composable
@Preview
fun GreetingPreview(){
    BallGame(xCoord = 1f, yCoord = 1f)
}