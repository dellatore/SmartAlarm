package com.example.alarme_inteligente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarme_inteligente.helper.AcelerometroHelper
import com.example.alarme_inteligente.model.SomPlayer

class AlarmeMovimentoActivity : AppCompatActivity(), AcelerometroHelper.Callback {

    private lateinit var helper: AcelerometroHelper
//    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)

//        player = MediaPlayer.create(this, R.raw.alarm_sound)
//        player.isLooping = true
//        player.start()

        helper = AcelerometroHelper(this, this)
    }

    override fun onResume() {
        super.onResume()
        helper.iniciar()
    }

    override fun onPause() {
        super.onPause()
        helper.parar()
    }

    override fun onChacoalhado() {
        pararAlarme()
    }

    private fun pararAlarme() {
        SomPlayer.pararSom()
        helper.parar()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        SomPlayer.pararSom()
    }
}
