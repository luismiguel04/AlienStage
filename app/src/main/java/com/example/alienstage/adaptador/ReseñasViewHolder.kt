package com.example.alienstage.adaptador

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.alienstage.DetalleSoportesActivity
import com.example.alienstage.R
import com.example.alienstage.Reseña
import com.example.alienstage.Soporte
import com.example.alienstage.databinding.ItemResenasBinding
import com.example.alienstage.databinding.ItemSoporteBinding

class ReseñasViewHolder (view: View): RecyclerView.ViewHolder(view){

    val binding = ItemResenasBinding.bind(view)

    fun render(ServicioModel: Reseña, onClickListener: (Reseña) ->Unit){
        binding.tvNota.text= ServicioModel.nota
        binding.tvCalificacion.text =ServicioModel.calificacion
        binding.tvRecomendacion.text =ServicioModel.recomendacion
        binding.tvFecha.text =ServicioModel.fecha

        // Selecciona la imagen según la recomendación
        val imagenResId = if (ServicioModel.recomendacion.equals("si")) {
            R.drawable.sentiment_satisfied_24px
        } else {
            R.drawable.sentiment_dissatisfied_24px
        }

        binding.ivImagen.setImageResource(imagenResId)
        binding.tvfechares.text =ServicioModel.fecharespuesta
        binding.tvnotares.text =ServicioModel.notarespuesta



        itemView.setOnClickListener {
            onClickListener(ServicioModel)

        }
    }


}