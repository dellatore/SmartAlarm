package com.example.alarme_inteligente.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.alarme_inteligente.MoveActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val movimento = intent.getBooleanExtra("movimento", false)

        Toast.makeText(context, "⏰ Alarme Disparado!", Toast.LENGTH_SHORT).show()

        if (movimento) {
            val i = Intent(context, MoveActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(i)
        } else {
            // aqui você pode abrir outra tela ou tocar um som diretamente
            // ou chamar uma Activity de voz, se preferir
        }
    }
}
