package com.example.alarme_inteligente.model


import android.content.Context
import android.media.MediaPlayer
import com.example.alarme_inteligente.R

object SomPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun tocarSom(context: Context) {
        pararSom()
        mediaPlayer = MediaPlayer.create(context, R.raw.mensagem2)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun pararSom() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
