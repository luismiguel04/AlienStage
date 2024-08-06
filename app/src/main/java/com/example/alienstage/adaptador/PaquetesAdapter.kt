package com.example.alienstage.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.R

import com.example.alienstage.paquete

class PaquetesAdapter (private val Paquetelista:List<paquete>, private val onClickListener: (paquete) ->Unit): RecyclerView.Adapter<PaquetesViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaquetesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PaquetesViewHolder(layoutInflater.inflate(R.layout.item_servicio,parent,false))

    }

    override fun getItemCount():Int= Paquetelista.size

    override fun onBindViewHolder(holder: PaquetesViewHolder, position: Int) {
        val item =Paquetelista[position]
        holder.render(item, onClickListener)

    }
}