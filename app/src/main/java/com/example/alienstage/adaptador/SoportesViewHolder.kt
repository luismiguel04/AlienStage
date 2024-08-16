package com.example.alienstage.adaptador

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alienstage.DetalleServicioActivity
import com.example.alienstage.Soporte
import com.example.alienstage.databinding.ItemSoporteBinding
import kotlinx.coroutines.CoroutineStart
import android.util.Base64
import com.example.alienstage.DetalleSoportesActivity

class SoportesViewHolder (view: View): RecyclerView.ViewHolder(view){

    val binding = ItemSoporteBinding.bind(view)

    fun render(ServicioModel:Soporte, onClickListener: (Soporte) ->Unit){
        binding.tvCorreo.text= ServicioModel.correo
        binding.tvSituacion.text =ServicioModel.situacion
        binding.tvDescripcion.text =ServicioModel.descripcion
        binding.tvFecha.text =ServicioModel.fecha
        binding.tvEstatus.text =ServicioModel.estatus


        fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                null
            }
        }

        val bitmap = decodeBase64ToBitmap(ServicioModel.evidencia)
        binding.ivEvidencia.setImageBitmap(bitmap)


        itemView.setOnClickListener {
            onClickListener(ServicioModel)



            val intent = Intent(itemView.context, DetalleSoportesActivity::class.java).apply {
                putExtra("idsoporte",ServicioModel.idSoporte.toString())
                putExtra("correo",ServicioModel.correo)
                putExtra("situacion",ServicioModel.situacion)
                putExtra("descripcion",ServicioModel.descripcion)
                putExtra("fecha",ServicioModel.fecha)
                putExtra("evidencia",ServicioModel.evidencia)
                putExtra("estatus",ServicioModel.estatus)
                putExtra("solucion",ServicioModel.solucion)


            }
            itemView.context.startActivity(intent)
        }
    }


}