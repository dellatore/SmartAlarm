package com.example.alarme_inteligente

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.alarme_inteligente.helper.ReconhecimentoHelper
import com.example.alarme_inteligente.model.SomPlayer

class AlarmeVozActivity : AppCompatActivity(), ReconhecimentoHelper.Callback {

    private lateinit var helper: ReconhecimentoHelper
//    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)

//        player = MediaPlayer.create(this, R.raw.alarm_sound)
//        player.isLooping = true
//        player.start()

        helper = ReconhecimentoHelper(this, this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
        }
        helper.iniciarReconhecimento()
    }

    override fun onReconhecimentoFinalizado(texto: String?) {
        if (texto?.contains("desligar", ignoreCase = true) == true) {
            pararAlarme()
        } else {
            helper.iniciarReconhecimento()
        }
    }

    override fun onErroReconhecimento(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        helper.iniciarReconhecimento()
    }

    private fun pararAlarme() {
        SomPlayer.pararSom()
        helper.liberar()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.liberar()
        SomPlayer.pararSom()
    }
}
