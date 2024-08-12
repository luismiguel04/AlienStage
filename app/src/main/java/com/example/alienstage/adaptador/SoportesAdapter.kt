package com.example.alienstage.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.R
import com.example.alienstage.Soporte

class SoportesAdapter (private val Soportelista:List<Soporte>, private val onClickListener: (Soporte) ->Unit): RecyclerView.Adapter<SoportesViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoportesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SoportesViewHolder(layoutInflater.inflate(R.layout.item_soporte,parent,false))

    }
    override fun getItemCount():Int= Soportelista.size

    override fun onBindViewHolder(holder: SoportesViewHolder, position: Int) {
        val item =Soportelista[position]
        holder.render(item, onClickListener)

    }
}