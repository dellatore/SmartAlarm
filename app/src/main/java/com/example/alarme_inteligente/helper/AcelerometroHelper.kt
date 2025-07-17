package com.example.alarme_inteligente.helper

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class AcelerometroHelper(
    private val context: Context,
    private val callback: Callback
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun iniciar() {
        acelerometro?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun parar() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]
            val aceleracao = sqrt(x * x + y * y + z * z)

            if (aceleracao > 15f) {
                callback.onChacoalhado()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    interface Callback {
        fun onChacoalhado()
    }
}
