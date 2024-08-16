package com.example.alienstage.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.R
import com.example.alienstage.Reseña
import com.example.alienstage.Soporte

class ReseñasAdapeter (private val Reseñaslista:List<Reseña>, private val onClickListener: (Reseña) ->Unit): RecyclerView.Adapter<ReseñasViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReseñasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReseñasViewHolder(layoutInflater.inflate(R.layout.item_resenas,parent,false))

    }
    override fun getItemCount():Int= Reseñaslista.size

    override fun onBindViewHolder(holder: ReseñasViewHolder, position: Int) {
        val item =Reseñaslista[position]
        holder.render(item, onClickListener)

    }
}