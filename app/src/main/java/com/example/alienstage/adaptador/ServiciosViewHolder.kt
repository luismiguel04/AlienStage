package com.example.alienstage.adaptador

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.Pago
import com.example.alienstage.Servicio
import com.example.alienstage.databinding.ItemServicioBinding

class ServiciosViewHolder(view: View): RecyclerView.ViewHolder(view){

    val binding = ItemServicioBinding.bind(view)


    fun render(ServicioModel: Pago, onClickListener: (Pago) ->Unit){
        binding.tvidServicio.text= ServicioModel.idServicio.toString()
        binding.tvNombre.text =ServicioModel.nombrep
        binding.tvDescripcion.text =ServicioModel.nombreser
        binding.tvEstatus.text=ServicioModel.estatus



        itemView.setOnClickListener {
            onClickListener(ServicioModel)
            val intent = Intent(itemView.context,DetalleServicioActivity::class.java).apply {
                putExtra("idservicio",ServicioModel.idServicio.toString())
                putExtra("nombre",ServicioModel.nombrep)
                putExtra("estatus",ServicioModel.estatus)

            }
            itemView.context.startActivity(intent)
        }
    }
}