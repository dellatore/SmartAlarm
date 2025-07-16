package com.example.alarme_inteligente.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarme_inteligente.R

class AlarmAdapter(private var alarmes: Array<String>) :
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtData: TextView = view.findViewById(R.id.tvAlarmeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = alarmes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtData.text = alarmes[position]
    }

    fun addAlarmes(novos: List<String>) {
        val atual = alarmes.toMutableList()
        atual.addAll(novos)
        alarmes = atual.toTypedArray()
        notifyDataSetChanged()
    }

    fun setAlarmes(novos: List<String>) {
        alarmes = novos.toTypedArray()
        notifyDataSetChanged()
    }
}
