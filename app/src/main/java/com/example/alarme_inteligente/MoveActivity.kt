//package com.example.alarme_inteligente
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.media.MediaPlayer
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import com.example.alarme_inteligente.databinding.ActivityMoveBinding
//import com.example.alarme_inteligente.helper.AcelerometroHelper
//import com.example.alarme_inteligente.helper.ReconhecimentoHelper
//
//class MoveActivity : AppCompatActivity(),
//    AcelerometroHelper.Callback,
//    ReconhecimentoHelper.Callback {
//
//    private lateinit var binding: ActivityMoveBinding
//    private lateinit var acelerometroHelper: AcelerometroHelper
//    private lateinit var reconhecimentoHelper: ReconhecimentoHelper
//    private var mediaPlayer: MediaPlayer? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMoveBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        acelerometroHelper = AcelerometroHelper(this, this)
//        reconhecimentoHelper = ReconhecimentoHelper(this, this)
//
//
//
//        // Inicia reconhecimento de voz
//        solicitarPermissaoMicrofone()
//
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        acelerometroHelper.iniciar()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        acelerometroHelper.parar()
//        reconhecimentoHelper.liberar()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release()
//    }
//
//    fun onMovimentoDetectado() {
//        desligarAlarme("Movimento detectado")
//    }
//
//    override fun onReconhecimentoFinalizado(texto: String?) {
//        if (texto != null && texto.lowercase().contains("desligar")) {
//            desligarAlarme("Palavra-chave reconhecida")
//        } else {
//            Toast.makeText(this, "Tente novamente. Diga: desligar", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onErroReconhecimento(mensagem: String) {
//        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun desligarAlarme(motivo: String) {
//        Toast.makeText(this, "Alarme desligado: $motivo", Toast.LENGTH_SHORT).show()
//        mediaPlayer?.stop()
//        finish()
//    }
//
//    private fun solicitarPermissaoMicrofone() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
//        }
//    }
//
//    override fun onChacoalhado() {
//
//    }
//}
