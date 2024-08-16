package com.example.alienstage.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.Novedad
import com.example.alienstage.R


class NovedadAdapter (private val Novedadlista:List<Novedad>, private val onClickListener: (Novedad) ->Unit): RecyclerView.Adapter<NovedadViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovedadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NovedadViewHolder(layoutInflater.inflate(R.layout.item_novedad,parent,false))

    }

    override fun getItemCount():Int= Novedadlista.size

    override fun onBindViewHolder(holder: NovedadViewHolder, position: Int) {
        val item =Novedadlista[position]
        holder.render(item, onClickListener)

    }
}