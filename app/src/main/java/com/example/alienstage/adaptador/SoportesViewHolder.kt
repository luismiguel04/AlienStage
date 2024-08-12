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

class SoportesViewHolder (view: View): RecyclerView.ViewHolder(view){

    val binding = ItemSoporteBinding.bind(view)

    fun render(ServicioModel:Soporte, onClickListener: (Soporte) ->Unit){
        binding.tvCorreo.text= ServicioModel.correo
        binding.tvSituacion.text =ServicioModel.situacion
        binding.tvDescripcion.text =ServicioModel.descripcion
        binding.tvFecha.text =ServicioModel.fecha

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



            val intent = Intent(itemView.context, DetalleServicioActivity::class.java).apply {
                putExtra("idservicio",ServicioModel.idSoporte.toString())
                putExtra("nombre",ServicioModel.correo)
                putExtra("precio",ServicioModel.situacion)
                putExtra("estatus",ServicioModel.descripcion)
                putExtra("descripcion",ServicioModel.fecha)
                putExtra("resena",ServicioModel.evidencia)


            }
            itemView.context.startActivity(intent)
        }
    }


}