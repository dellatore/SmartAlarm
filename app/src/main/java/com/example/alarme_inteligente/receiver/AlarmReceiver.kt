package com.example.alarme_inteligente.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.alarme_inteligente.AlarmeMovimentoActivity
import com.example.alarme_inteligente.AlarmeVozActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val tipo = intent.getStringExtra("tipo")

        val activityClass = when (tipo) {
            "movimento" -> AlarmeMovimentoActivity::class.java
            else -> AlarmeVozActivity::class.java
        }

        val activityIntent = Intent(context, activityClass).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(activityIntent)
    }
}

