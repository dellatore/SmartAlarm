package com.example.alarme_inteligente

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarme_inteligente.CadastroAlarmeActivity
import com.example.alarme_inteligente.adapter.AlarmAdapter
import com.example.alarme_inteligente.databinding.ActivityMainBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore
    private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private lateinit var adapter: AlarmAdapter
    private val alarmes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup do RecyclerView
        adapter = AlarmAdapter(alarmes.toTypedArray())
        binding.recyclerAlarms.layoutManager = LinearLayoutManager(this)
        binding.recyclerAlarms.adapter = adapter

        // Botão de adicionar alarme
        binding.btnAddAlarm.setOnClickListener {
            startActivity(Intent(this, CadastroAlarmeActivity::class.java))
        }

        carregarAlarmes()
    }

    override fun onResume() {
        super.onResume()
        carregarAlarmes()
    }


    private fun carregarAlarmes() {
        db.collection("alarme")
            .orderBy("data", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Toast.makeText(this, "Nenhum alarme encontrado.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val listaAlarmes = mutableListOf<String>()
                val agora = System.currentTimeMillis()
                var proximoAlarme: Long? = null

                result.documents.forEach { doc ->
                    val data = doc.getTimestamp("data")?.toDate()?.time
                    data?.let {
                        listaAlarmes.add(sdf.format(Date(it)))
                        if (it > agora && (proximoAlarme == null || it < proximoAlarme!!)) {
                            proximoAlarme = it
                        }
                    }
                }

                binding.tvNextAlarmTime.text = proximoAlarme?.let {
                    sdf.format(Date(it))
                } ?: "Nenhum"

                adapter.setAlarmes(listaAlarmes)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao buscar alarmes", Toast.LENGTH_SHORT).show()
            }
    }

}
