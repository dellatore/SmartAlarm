package com.example.alarme_inteligente


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarme_inteligente.databinding.ActivityNewAlarmBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CadastroAlarmeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewAlarmBinding
    private val db = FirebaseFirestore.getInstance()

    private val calendar = Calendar.getInstance()

    private val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())

    private var desativarPorVoz = false
    private var desativarPorMovimento = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        atualizarDataHoraViews()

        binding.tvDataSelecionada.setOnClickListener {
            val dp = DatePickerDialog(this, { _, y, m, d ->
                calendar.set(Calendar.YEAR, y)
                calendar.set(Calendar.MONTH, m)
                calendar.set(Calendar.DAY_OF_MONTH, d)
                atualizarDataHoraViews()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dp.show()
        }

        binding.tvHoraSelecionada.setOnClickListener {
            val tp = TimePickerDialog(this, { _, h, m ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                atualizarDataHoraViews()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            tp.show()
        }

        binding.btnConcluir.setOnClickListener {
            salvarAlarme()
        }

        binding.btnToggleVoz.setOnClickListener {
            desativarPorVoz = true
            desativarPorMovimento = false
            atualizarBotaoVoz()
            atualizarBotaoMovimento()
        }

        binding.btnToggleMovimento.setOnClickListener {
            desativarPorMovimento = true
            desativarPorVoz = false
            atualizarBotaoMovimento()
            atualizarBotaoVoz()
        }


        atualizarBotaoVoz()
        atualizarBotaoMovimento()

    }


    private fun atualizarDataHoraViews() {
        binding.tvDataSelecionada.text = sdfDate.format(calendar.time)
        binding.tvHoraSelecionada.text = sdfTime.format(calendar.time)
    }

    private fun salvarAlarme() {
        val dataSelecionada = calendar.time

        if (dataSelecionada.time <= System.currentTimeMillis()) {
            Toast.makeText(this, "Selecione uma data/hora futura", Toast.LENGTH_SHORT).show()
            return
        }

        val tipo = when {
            desativarPorMovimento -> "movimento"
            desativarPorVoz -> "voz"
            else -> null
        }

        if (tipo == null) {
            Toast.makeText(this, "Selecione pelo menos uma forma de desativação", Toast.LENGTH_SHORT).show()
            return
        }

        val alarme = hashMapOf(
            "data" to Timestamp(dataSelecionada),
            "tipo" to tipo,
            "criadoEm" to Timestamp.now()
        )

        db.collection("alarme")
            .add(alarme)
            .addOnSuccessListener {
                Toast.makeText(this, "Alarme salvo com sucesso!", Toast.LENGTH_SHORT).show()
                agendarDisparo(dataSelecionada.time, tipo)
                finish()
            }
    }



    private fun agendarDisparo(dataMillis: Long, tipo: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Permissão necessária para alarmes exatos", Toast.LENGTH_LONG).show()
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
                return
            }
        }

        val intent = Intent(this, com.example.alarme_inteligente.receiver.AlarmReceiver::class.java).apply {
            putExtra("tipo", tipo)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            dataMillis.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                dataMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao agendar alarme. Verifique as permissões.", Toast.LENGTH_SHORT).show()
        }
    }



    private fun atualizarBotaoVoz() {
        binding.btnToggleVoz.text = "Desativar por voz: ${if (desativarPorVoz) "ON" else "OFF"}"
        val bg = if (desativarPorVoz) R.drawable.button_on else R.drawable.button_off
        binding.btnToggleVoz.setBackgroundResource(bg)
    }

    private fun atualizarBotaoMovimento() {
        binding.btnToggleMovimento.text = "Desativar por movimento: ${if (desativarPorMovimento) "ON" else "OFF"}"
        val bg = if (desativarPorMovimento) R.drawable.button_on else R.drawable.button_off
        binding.btnToggleMovimento.setBackgroundResource(bg)
    }

}
