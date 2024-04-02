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

    var insetHori by mutableStateOf(1f)
    var insetVerti by mutableStateOf(1f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSensor()
        setContent {
            KiihtyvyysharjoitusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", insetHori = insetHori, insetVerti = insetVerti)
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
            //Log.d("Sensori", event.values[0].toString()) // x
            //Log.d("Sensori", event.values[1].toString()) // y
            //Log.d("Sensori", event.values[2].toString()) // z

            if(event.values[0] < 0 || event.values[0] > 0){
                insetHori -= event.values[0]*10
            }else{
                insetHori = 1f
            }
            if (insetHori<100f){
                insetHori = 100f
            }else if (insetHori> xMax-100f){
                insetHori = xMax -100f
            }

            if(event.values[1] < 0 || event.values[1] > 0){
                insetVerti += event.values[1]*10
            }else{
                insetVerti = 1f
            }
            if (insetVerti<100f){
                insetVerti = 100f
            }else if (insetVerti> yMax-100f){
                insetVerti = yMax -100f
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, insetHori: Float, insetVerti: Float) {
    Canvas(modifier = Modifier.fillMaxSize()){
        drawCircle(
            color = Color.Red,
            center = Offset(x = insetHori, y = insetVerti),
            radius = 100f
        )
    }
}

@Composable
@Preview
fun GreetingPreview(){
    Greeting(name = "Hello", insetHori = 1f, insetVerti = 1f)
}