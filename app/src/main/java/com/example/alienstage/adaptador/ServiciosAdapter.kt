package com.example.alienstage.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.Pago
import com.example.alienstage.R
import com.example.alienstage.Servicio


class ServiciosAdapter ( private val superheroelista:List<Pago>, private val onClickListener: (Pago) ->Unit): RecyclerView.Adapter<ServiciosViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiciosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ServiciosViewHolder(layoutInflater.inflate(R.layout.item_pago,parent,false))

    }

    override fun getItemCount():Int= superheroelista.size

    override fun onBindViewHolder(holder: ServiciosViewHolder, position: Int) {
        val item =superheroelista[position]
        holder.render(item, onClickListener)

    }
}