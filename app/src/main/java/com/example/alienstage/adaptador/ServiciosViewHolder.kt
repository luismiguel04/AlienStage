package com.example.alienstage.adaptador

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.Servicio
import com.example.alienstage.databinding.ItemServicioBinding

class ServiciosViewHolder(view: View): RecyclerView.ViewHolder(view){

    val binding = ItemServicioBinding.bind(view)


    fun render(ServicioModel: Servicio, onClickListener: (Servicio) ->Unit){
        binding.tvidServicio.text= ServicioModel.idServicio.toString()
        binding.tvNombre.text =ServicioModel.nombreServicio
        binding.tvDescripcion.text =ServicioModel.descripcion
        binding.tvEstatus.text=ServicioModel.estatus
        Glide.with(binding.ivImagen.context).load(ServicioModel.imagen).into(binding.ivImagen)

        itemView.setOnClickListener {
            onClickListener(ServicioModel)
            val intent = Intent(itemView.context,DetalleServicioActivity::class.java).apply {
                putExtra("idservicio",ServicioModel.idServicio.toString())
                putExtra("nombre",ServicioModel.nombreServicio)
                putExtra("estatus",ServicioModel.estatus)
                putExtra("descripcion",ServicioModel.descripcion)
                putExtra("imagen",ServicioModel.imagen)

            }
            itemView.context.startActivity(intent)
        }
    }
}