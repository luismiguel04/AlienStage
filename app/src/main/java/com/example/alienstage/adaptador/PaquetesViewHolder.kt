package com.example.alienstage.adaptador


import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.databinding.ItemServicioBinding
import com.example.alienstage.paquete

class PaquetesViewHolder(view: View): RecyclerView.ViewHolder(view){

    val binding = ItemServicioBinding.bind(view)





    fun render(ServicioModel: paquete, onClickListener: (paquete) ->Unit){
        binding.tvidServicio.text= ServicioModel.idPaquete.toString()
        binding.tvNombre.text =ServicioModel.nombre
        binding.tvDescripcion.text =ServicioModel.descripcion
        //binding.tvDescripcion.text =ServicioModel.precio.toString()
        //binding.tvDescripcion.text =ServicioModel.idservicio.toString()
        //binding.tvEstatus.text=ServicioModel.resena
        binding.tvEstatus.text=ServicioModel.estatus
        Glide.with(binding.ivImagen.context).load(ServicioModel.foto).into(binding.ivImagen)

        itemView.setOnClickListener {
            onClickListener(ServicioModel)
            val intent = Intent(itemView.context,DetalleServicioActivity::class.java).apply {
                putExtra("idservicio",ServicioModel.idPaquete.toString())
                putExtra("nombre",ServicioModel.nombre)
                putExtra("precio",ServicioModel.precio.toString())
                putExtra("estatus",ServicioModel.estatus)
                putExtra("descripcion",ServicioModel.descripcion)
                putExtra("resena",ServicioModel.resena)
                putExtra("imagen",ServicioModel.foto)

            }
            itemView.context.startActivity(intent)
        }
    }
}